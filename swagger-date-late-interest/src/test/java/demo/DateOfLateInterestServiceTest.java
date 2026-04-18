package demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import demo.service.DateOfLateInterestService;

public class DateOfLateInterestServiceTest {
   
   @Test
   public void testCalculateDateOfInterest() {
      //public static LocalDate calculateLatePaymentInterestDate(LocalDate dateBilan, LocalDate uitvoerDatum, LocalDate verzendingDatum, double taux, int taxYear)
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2024-12-31"), LocalDate.parse("2026-07-10"), LocalDate.parse("2026-07-11"), 5000, 2025), LocalDate.parse("2026-07-01")); // Example 1
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2025-09-30"), LocalDate.parse("2026-11-10"), LocalDate.parse("2026-11-11"), 5000, 2025), LocalDate.parse("2026-10-01")); // Example 2      
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2025-09-30"), LocalDate.parse("2026-08-10"), LocalDate.parse("2026-08-11"), 5000, 2025), LocalDate.parse("2026-11-01")); // Example 3
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2024-12-30"), LocalDate.parse("2026-02-05"), LocalDate.parse("2026-02-06"), 5000, 2024), LocalDate.parse("2026-01-01")); // Example 4
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2024-12-30"), LocalDate.parse("2025-12-10"), LocalDate.parse("2025-12-11"), 5000, 2024), LocalDate.parse("2026-03-01")); // Example 5
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2025-06-30"), LocalDate.parse("2026-05-01"), LocalDate.parse("2026-05-02"), 5000, 2025), LocalDate.parse("2026-08-01")); // Example 6
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2025-06-30"), LocalDate.parse("2026-12-30"), LocalDate.parse("2026-12-31"), 5000, 2025), LocalDate.parse("2026-07-01")); // Example 7
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2025-04-30"), LocalDate.parse("2026-08-01"), LocalDate.parse("2026-08-02"), 5000, 2025), LocalDate.parse("2026-07-01")); // Example 8
      assertEquals(DateOfLateInterestService.calculateDateOfLateInterest(LocalDate.parse("2025-04-30"), LocalDate.parse("2026-03-25"), LocalDate.parse("2026-03-26"), 5000, 2025), LocalDate.parse("2026-06-01")); // Example 9

      
  }


}
