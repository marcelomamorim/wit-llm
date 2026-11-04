/*
 * @test
 * @summary Verifies LocalDate.getDayOfYear() for leap and non-leap years and consistency with DAY_OF_YEAR
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        check(LocalDate.of(2018, 1, 1).getDayOfYear(), 1, "2018-01-01");
        check(LocalDate.of(2018, 12, 31).getDayOfYear(), 365, "2018-12-31");
        check(LocalDate.of(2016, 12, 31).getDayOfYear(), 366, "2016-12-31 leap year");
        check(LocalDate.of(2018, 3, 1).getDayOfYear(), 60, "2018-03-01");
        check(LocalDate.of(2016, 3, 1).getDayOfYear(), 61, "2016-03-01 leap year");
        check(LocalDate.of(2000, Month.FEBRUARY, 29).getDayOfYear(), 60, "2000-02-29");
        check(LocalDate.of(1900, 12, 31).getDayOfYear(), 365, "1900-12-31 not leap");
        check(LocalDate.of(2018, Month.DECEMBER, 1).getDayOfYear(), 335, "2018-12-01");

        LocalDate d = LocalDate.of(2016, 1, 1);
        for (int i = 0; i < 800; i++) {
            int doy = d.getDayOfYear();
            if (doy != d.get(ChronoField.DAY_OF_YEAR)) {
                throw new RuntimeException("Mismatch with DAY_OF_YEAR at " + d);
            }
            if (doy < 1 || doy > 366) {
                throw new RuntimeException("Out of range day of year " + doy + " at " + d);
            }
            LocalDate next = d.plusDays(1);
            int nextDoy = next.getDayOfYear();
            if (nextDoy != 1 && nextDoy != doy + 1) {
                throw new RuntimeException("Non-sequential day of year after " + d);
            }
            d = next;
        }
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected getDayOfYear() for " + label
                    + ": expected " + expected + " but was " + actual);
        }
    }
}
