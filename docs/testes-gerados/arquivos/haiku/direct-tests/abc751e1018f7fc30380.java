/*
 * @test
 * @summary Tests LocalDate.getDayOfYear for leap and non-leap years and sequential days
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        check(LocalDate.of(2007, 1, 1).getDayOfYear(), 1, "2007-01-01");
        check(LocalDate.of(2007, 12, 31).getDayOfYear(), 365, "2007-12-31");
        check(LocalDate.of(2008, 2, 29).getDayOfYear(), 60, "2008-02-29");
        check(LocalDate.of(2008, 3, 1).getDayOfYear(), 61, "2008-03-01");
        check(LocalDate.of(2007, 3, 1).getDayOfYear(), 60, "2007-03-01");
        check(LocalDate.of(2008, 12, 31).getDayOfYear(), 366, "2008-12-31");
        check(LocalDate.of(2000, 12, 31).getDayOfYear(), 366, "2000-12-31");
        check(LocalDate.of(1900, 12, 31).getDayOfYear(), 365, "1900-12-31");
        check(LocalDate.of(2400, 12, 31).getDayOfYear(), 366, "2400-12-31");

        LocalDate d = LocalDate.of(2008, 1, 1);
        int expected = 1;
        while (d.getYear() == 2008) {
            check(d.getDayOfYear(), expected, d.toString());
            expected++;
            d = d.plusDays(1);
        }
        check(expected - 1, 366, "days in 2008");

        for (int year = 1899; year <= 1901; year++) {
            LocalDate start = LocalDate.of(year, 1, 1);
            LocalDate current = start;
            int count = 1;
            while (current.getYear() == year) {
                check(current.getDayOfYear(), count, current.toString());
                count++;
                current = current.plusDays(1);
            }
        }
    }

    static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected value for " + label
                    + ": expected " + expected + " but was " + actual);
        }
    }
}
