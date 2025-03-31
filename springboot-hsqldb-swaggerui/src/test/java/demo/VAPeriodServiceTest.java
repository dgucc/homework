package demo;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import demo.service.VAPeriodService;

public class VAPeriodServiceTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testGetFictiveBalanceDates() {

        assertEquals("Get Fictive Balance Date", VAPeriodService.getFictiveBalanceDate(LocalDate.parse("2023-10-01"), LocalDate.parse("2024-07-04")), LocalDate.parse("2024-09-30"));
        assertEquals("Get Fictive Balance Date", VAPeriodService.getFictiveBalanceDate(LocalDate.parse("2023-10-02"), LocalDate.parse("2024-07-04")), LocalDate.parse("2024-07-31"));
        assertEquals("Get Fictive Balance Date", VAPeriodService.getFictiveBalanceDate(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-12-31")), LocalDate.parse("2024-02-29"));
        assertEquals("Get Fictive Balance Date", VAPeriodService.getFictiveBalanceDate(LocalDate.parse("2023-06-04"), LocalDate.parse("2023-12-31")), LocalDate.parse("2023-12-31"));
        assertEquals("Get Fictive Balance Date", VAPeriodService.getFictiveBalanceDate(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-09-30")), LocalDate.parse("2023-11-30"));
        assertEquals("Get Fictive Balance Date", VAPeriodService.getFictiveBalanceDate(LocalDate.parse("2023-06-15"), LocalDate.parse("2023-09-30")), LocalDate.parse("2023-09-30"));
    }

    @Test
    public void testIsTaxYearAtLeastOneYear(){
        assertFalse("At least One Year ? 10", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-10-01"), LocalDate.parse("2024-07-04")));
        assertFalse("At least One Year ? 9", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-10-02"), LocalDate.parse("2024-07-04")));
        assertFalse("At least One Year ? 7", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-12-31")));
        assertFalse("At least One Year ? 6", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-04"), LocalDate.parse("2023-12-31")));
        assertFalse("At least One Year ? 4", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-09-30")));
        assertFalse("At least One Year ? 3", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-15"), LocalDate.parse("2023-09-30")));

        assertFalse("At least One Year ? 7", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-12-31")));
        assertFalse("At least One Year ? 10", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-02-15"), LocalDate.parse("2023-12-31")));
        assertFalse("At least One Year ? 9", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-03-15"), LocalDate.parse("2023-12-31")));
        assertFalse("At least One Year ? 2", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-10-15"), LocalDate.parse("2023-12-31")));

        assertTrue("At least One Year  ? 12", VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-12"), LocalDate.parse("2024-11-30")));

    }

    @Test
    public void testTaxYearType(){
        boolean isFirstActivity = true;
        boolean isNotFirstActivity = false;

        // 12 mois et pas 1re activité -> NORM
        assertEquals(VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-06-12"), LocalDate.parse("2024-11-30")) || isNotFirstActivity ? "NORM" : "SPEC", "NORM");
        // 2 mois mais 1re activité -> NORM
        assertEquals(VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-10-15"), LocalDate.parse("2023-12-31")) || isFirstActivity ? "NORM" : "SPEC", "NORM");
        // 2 mois et pas 1re activité -> SPEC
        assertEquals(VAPeriodService.isTaxYearAtLeastOneYear(LocalDate.parse("2023-10-15"), LocalDate.parse("2023-12-31")) || isNotFirstActivity ? "NORM" : "SPEC", "SPEC");
    }
}
