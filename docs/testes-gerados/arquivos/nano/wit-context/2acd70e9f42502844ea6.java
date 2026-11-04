/*
 * @test
 * @summary Test for LocalDate.withDayOfMonth(int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithDayOfMonthValid();
        testWithDayOfMonthInvalid();
        testWithDayOfMonthSame();
    }

    static void testWithDayOfMonthValid() {
        LocalDate date = LocalDate.of(2021, Month.MARCH, 15);
        int validDay = 20;
        LocalDate result = date.withDayOfMonth(validDay);
        if (result.getYear() != 2021 || result.getMonth() != Month.MARCH || result.getDayOfMonth() != validDay) {
            throw new AssertionError("Failed valid withDayOfMonth: expected 2021-03-20, got " + result);
        }
    }

    static void testWithDayOfMonthInvalid() {
        LocalDate date = LocalDate.of(2021, Month.FEBRUARY, 28);
        int invalidDay = 30;
        try {
            date.withDayOfMonth(invalidDay);
            throw new AssertionError("Expected DateTimeException for invalid day")
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testWithDayOfMonthSame() {
        LocalDate date = LocalDate.of(2021, Month.JANUARY, 10);
        int sameDay = 10;
        LocalDate result = date.withDayOfMonth(sameDay);
        if (result != date) {
            throw new AssertionError("Expected same instance when day is unchanged")
        }
    }
}
