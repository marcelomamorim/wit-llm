/*
 * @test
 * @summary Test java.time.LocalDate.withDayOfMonth(int) method for valid and invalid inputs
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithDayOfMonthValid();
        testWithDayOfMonthSameDayReturnsThis();
        testWithDayOfMonthInvalidDayThrows();
    }

    private static void testWithDayOfMonthValid() {
        LocalDate date = LocalDate.of(2018, 6, 15);
        LocalDate changed = date.withDayOfMonth(20);
        if (changed.getDayOfMonth() != 20) {
            throw new AssertionError("Expected dayOfMonth 20 but got " + changed.getDayOfMonth());
        }
        if (changed.getYear() != 2018 || changed.getMonthValue() != 6) {
            throw new AssertionError("Year or month changed unexpectedly");
        }
    }

    private static void testWithDayOfMonthSameDayReturnsThis() {
        LocalDate date = LocalDate.of(2018, 6, 15);
        LocalDate result = date.withDayOfMonth(15);
        if (result != date) {
            throw new AssertionError("Expected same instance when dayOfMonth is unchanged");
        }
    }

    private static void testWithDayOfMonthInvalidDayThrows() {
        LocalDate date = LocalDate.of(2018, 6, 15);
        boolean thrown = false;
        try {
            date.withDayOfMonth(0); // invalid day
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for dayOfMonth=0");
        }

        thrown = false;
        try {
            date.withDayOfMonth(32); // invalid day
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for dayOfMonth=32");
        }
    }
}
