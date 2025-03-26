package demo.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class VAPeriodTest {

   public static void main(String[] args) {

       getFictiveBalanceDate(LocalDate.parse("2023-10-01"), LocalDate.parse("2024-07-04"));
       getFictiveBalanceDate(LocalDate.parse("2023-10-02"), LocalDate.parse("2024-07-04"));
       getFictiveBalanceDate(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-12-31"));
       getFictiveBalanceDate(LocalDate.parse("2023-06-04"), LocalDate.parse("2023-12-31"));
       getFictiveBalanceDate(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-09-30"));
       getFictiveBalanceDate(LocalDate.parse("2023-06-15"), LocalDate.parse("2023-09-30"));


   }


    private static long getMnd(LocalDate taxYearStartDate, LocalDate balanceDate){
        long result = 0;
        result = Math.abs(ChronoUnit.MONTHS.between(taxYearStartDate, balanceDate));
        if(taxYearStartDate.getDayOfMonth() == 1) {
            result = result + 1;
        }
        return result;
    }

    private static LocalDate getFictiveBalanceDate(LocalDate startTaxYear, LocalDate balanceDate){
       long MND = getMnd(startTaxYear, balanceDate); // nombre de mois entre les 2 dates
       long FICTMND = Math.floorDiv(MND+2, 3) * 3; // nombre de mois fictif
       LocalDate fictiveDate = balanceDate.plusMonths((int) (FICTMND-MND));
       fictiveDate = fictiveDate.with(TemporalAdjusters.lastDayOfMonth()); // dernier jour du mois
        System.out.println("getFictiveBalanceDate() : [" + startTaxYear + ", " + balanceDate + "] -> " + fictiveDate);
       return fictiveDate;
   }



}
