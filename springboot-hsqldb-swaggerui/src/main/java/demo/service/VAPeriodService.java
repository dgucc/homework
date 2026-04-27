package demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.model.VAPeriodDetail;
import demo.model.VARequest;
import demo.model.VAResponse;
import demo.repository.VAPeriodRepository;

@Service
public class VAPeriodService {

    private static final Logger logger = LoggerFactory.getLogger(VAPeriodService.class);

    @Autowired
    private VAPeriodRepository vAPeriodRepository;

    public VAResponse getVAPeriodsForPayments(VARequest request){

        VAResponse response = new VAResponse();
        response.setIsFirstActivity(request.getIsFirstActivity());
        response.setTaxYearStartDate(request.getTaxYearStartDate());
        response.setBalanceDate(request.getBalanceDate());
        response.setVaPeriods(new ArrayList<VAPeriodDetail>());

        // Define taxYear
        long taxYear = request.getBalanceDate().plusDays(1).getYear();

        // Define taxYearType {NORM,SPEC}
        String taxYearType = isTaxYearAtLeastOneYear(request.getTaxYearStartDate(), request.getBalanceDate())  || request.getIsFirstActivity() ? "NORM": "SPEC";

        // Define Fictive Balance Date
        if (taxYearType.equalsIgnoreCase("SPEC")) {
            response.setFictiveBalanceDate(getFictiveBalanceDate(request.getTaxYearStartDate(), request.getBalanceDate()));
            request.setBalanceDate(getFictiveBalanceDate(request.getTaxYearStartDate(), request.getBalanceDate()));
        }


        // For each Payment quey the database
        request.getPayments().forEach(payment -> {
            logger.info(
                "[" + this.getClass().getName() + " request] taxYear:" + taxYear + ",taxYearType:" + taxYearType + ",balanceDate:" + request.getBalanceDate() + ",amount:" + payment.getAmount() + ",paymentDate:" + payment.getPaymentDate());;
            
            // Query database
            VAPeriodDetail vaPeriod = vAPeriodRepository.getVAPeriod(taxYear, taxYearType, Date.valueOf(request.getBalanceDate()), payment.getAmount(), Date.valueOf(payment.getPaymentDate()));

            if(vaPeriod==null){
                response.getVaPeriods().add(null);    
            } else {
                // Post Validations : add error messages
                vaPeriod.setMessages(ValidateResponse(vaPeriod));
    
                logger.info("[" + this.getClass().getName() + " response] amount:" + payment.getAmount() + ",paymentDate:" + payment.getPaymentDate() + ",vaPeriod:" + vaPeriod.getVaPeriod() + ",vaMaxDate:" + vaPeriod.getVaMaxDate());
                
                response.getVaPeriods().add(vaPeriod);                                   
            }
        });


        return response;
    }

    private static long getMnd(LocalDate taxYearStartDate, LocalDate balanceDate){
        long result = 0;
        result = Math.abs(ChronoUnit.MONTHS.between(taxYearStartDate, balanceDate));
        if(taxYearStartDate.getDayOfMonth() == 1) {
            result = result + 1;
        }
        return result;
    }

    // Post-Validations
    private static List<String> ValidateResponse(VAPeriodDetail vaPeriodDetail){
        List<String> messages = new ArrayList<>();

        if(vaPeriodDetail == null){
            messages.add("ERROR : no vaPeriod found");

        }else if(vaPeriodDetail.getVaPeriod().equals("VA_PERIOD_4") && 
            vaPeriodDetail.getPaymentDate().compareTo(vaPeriodDetail.getVaMaxDate()) == 0){            
            messages.add("ERROR : the payment date is outside the year covered by the VA");
        }

        return messages;
    }

    public static LocalDate getFictiveBalanceDate(LocalDate taxYearStartDate, LocalDate balanceDate){
        long MND = getMnd(taxYearStartDate, balanceDate); // nombre de mois entre les 2 dates
        long FICTMND = Math.floorDiv(MND+2, 3) * 3; // nombre fictif de mois
        LocalDate fictiveDate = balanceDate.plusMonths((int) (FICTMND-MND)); // ajouter autant de mois que FICTMND-MND
        fictiveDate = fictiveDate.with(TemporalAdjusters.lastDayOfMonth()); // sélectionner le dernier jour du mois obtenu
        return fictiveDate;
    }

    public static boolean isTaxYearAtLeastOneYear(LocalDate taxYearStartDate, LocalDate balanceDate){
        boolean result = false;
        result = taxYearStartDate.plusYears(1).isBefore(balanceDate.plusDays(1)) ||
                taxYearStartDate.plusYears(1).isEqual(balanceDate.plusDays(1));
        return result;
    }


}
