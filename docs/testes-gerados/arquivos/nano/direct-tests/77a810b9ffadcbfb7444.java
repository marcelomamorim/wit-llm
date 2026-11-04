/*
 * @test
 * @summary Test for MinguoDate.until(ChronoLocalDate endDate)
 * @run main MinguoDateWitupTest
 */

import java.time.chrono.MinguoDate;
import java.time.chrono.ChronoLocalDate;
import java.time.Period;
import java.time.chrono.MinguoChronology;

public class MinguoDateWitupTest {

    public static void main(String[] args) {
        testUntilSameDate();
        testUntilDifferentDate();
        testUntilNull();
    }

    static void testUntilSameDate() {
        MinguoDate date = MinguoDate.of(100, 5, 15);
        ChronoLocalDate endDate = date;
        try {
            ChronoPeriod period = date.until(endDate);
            assert period.getYears() == 0 : "Expected 0 years";
            assert period.getMonths() == 0 : "Expected 0 months";
            assert period.getDays() == 0 : "Expected 0 days";
        } catch (Exception e) {
            throw new AssertionError("Failed testUntilSameDate", e);
        }
    }

    static void testUntilDifferentDate() {
        MinguoDate startDate = MinguoDate.of(100, 1, 1);
        MinguoDate endDate = MinguoDate.of(102, 3, 10);
        try {
            java.time.chrono.ChronoPeriod period = startDate.until(endDate);
            assert period.getYears() == 2 : "Expected 2 years";
            assert period.getMonths() == 2 : "Expected 2 months";
            assert period.getDays() == 9 : "Expected 9 days";
        } catch (Exception e) {
            throw new AssertionError("Failed testUntilDifferentDate", e);
        }
    }

    static void testUntilNull() {
        MinguoDate date = MinguoDate.of(100, 5, 15);
        try {
            date.until(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
