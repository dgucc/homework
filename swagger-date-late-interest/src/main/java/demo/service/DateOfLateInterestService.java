package demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import demo.dto.DateOfLateInterestRequest;
import demo.dto.DateOfLateInterestResponse;

@Service
public class DateOfLateInterestService {

    private static final Logger logger = LoggerFactory.getLogger(DateOfLateInterestService.class);
    private static List<String> calculationRules;

    public DateOfLateInterestResponse getDateOfLateInterest(DateOfLateInterestRequest request){

        DateOfLateInterestResponse response = new DateOfLateInterestResponse();
        response.setDateOfLateInterest(calculateDateOfLateInterest(
            request.getBalanceDate(),
            request.getExecutionDate(),
            request.getSendingDate(),
            request.getRate(),
            request.getTaxYear()));
        
        response.setCalculationRules(calculationRules);

        return response;
    }


    public static LocalDate calculateDateOfLateInterest(LocalDate dateBilan, LocalDate uitvoerDatum, LocalDate verzendingDatum, Integer taux, Integer taxYear) {
        calculationRules = new ArrayList<String>();
        /* ********************************************************************************** */
        /*                            *** Check date exécutoire ***                           */
        /* ********************************************************************************** */
        LocalDate taxYearJulyPlusOneYear = LocalDate.of(taxYear+1, 7, 1);
        if (uitvoerDatum.isBefore(taxYearJulyPlusOneYear)) {
            // Bord.UITVOERDATUMMANU < 01/07/Bord.TAXYEAR + 1 year
            calculationRules.add( "Bord.UITVOERDATUMMANU < 01/07/Bord.TAXYEAR + 1 year");
            calculationRules.add("General Rule : Scenario (A)");
            return applyGeneralRule(verzendingDatum); // General Rule : Scenario (A)
        } else {
            // Bord.UITVOERDATUMMANU >= 01/07/Bord.TAXYEAR + 1 year
            calculationRules.add( "Bord.UITVOERDATUMMANU >= 01/07/Bord.TAXYEAR + 1 year");
            /* ********************************************************************************** */
            /*                        *** Check taux d'accroissement ***                          */
            /* ********************************************************************************** */
            if (taux < 5000) {
                // Bord.TAUX (ou Bord.TAUXPM) < 5000
                calculationRules.add( "Bord.TAUX (ou Bord.TAUXPM) < 5000");
                calculationRules.add("General Rule : Scenario (A)");
                return applyGeneralRule(verzendingDatum); // General Rule : Scenario (A)

            } else {
                // Bord.TAUX (ou Bord.TAUXPM) >= 5000
                calculationRules.add( "Bord.TAUX (ou Bord.TAUXPM) >= 5000");

                /* ********************************************************************************** */
                /*                             *** Check date bilan ***                               */
                /* ********************************************************************************** */
                if (dateBilan.isBefore(LocalDate.of(taxYear,7,1))){
                    // Bord.DATEBILAN < 01/07/Bord.TAXYEAR
                    calculationRules.add("Bord.DATEBILAN < 01/07/Bord.TAXYEAR");
                    calculationRules.add("Specific Rule : Scenario (C)");
                    calculationRules.add("01 / 07 / Bord.TAXYEAR + 1");
                    return LocalDate.of(taxYear+1,7,1); // Scenario (C)

                } else {
                    calculationRules.add("Bord.DATEBILAN >= 01/07/Bord.TAXYEAR");
                    /* ********************************************************************************** */
                    /*                           *** Check date exécutoire (Bis) ***                      */
                    /* ********************************************************************************** */
                    if (uitvoerDatum.isBefore(dateBilan.plusYears(1))){
                        // Bord.UITVOERDATUMMANU < Bord.DATEBILAN + 1 year
                        calculationRules.add("Bord.UITVOERDATUMMANU < Bord.DATEBILAN + 1 year");
                        calculationRules.add("General Rule : Scenario (A)");
                        return applyGeneralRule(verzendingDatum); // General Rule : Scenario (A)

                    } else {
                        // Bord.UITVOERDATUMMANU >= Bord.DATEBILAN + 1 year
                        calculationRules.add("Bord.UITVOERDATUMMANU >= Bord.DATEBILAN + 1 year");
                        calculationRules.add("Specific Rule : Scenario (B)");
                        calculationRules.add("01 / Bord.DATEBILAN + 13 months");
                        return dateBilan.plusYears(1).plusMonths(1).withDayOfMonth(1); // Scenarion (B)
                    }
                }
            }
        }
    }

    // Calculer la date d'intérêt selon la règle générale (A)
    private static LocalDate applyGeneralRule(LocalDate verzendingDatum) {
        calculationRules.add("01 / Bord.VERZENDINGDATUM + 3 months");
        // 01 / Bord.VERZENDINGDATUM + 3 months
        return verzendingDatum.plusMonths(3).withDayOfMonth(1);
    }


}
