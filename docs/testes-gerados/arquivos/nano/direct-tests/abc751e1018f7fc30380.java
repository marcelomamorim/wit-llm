/*
 * @test    @summary Test getDayOfYear() method of LocalDate for various dates
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import static java.time.Month.JANUARY;
import static java.time.Month.DECEMBER;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testGetDayOfYearForJanuaryFirst();
        testGetDayOfYearForDecember31st();
        testGetDayOfYearForLeapYear();
        testGetDayOfYearForNonLeapYear();
    }

    static void testGetDayOfYearForJanuaryFirst() {
        LocalDate date = LocalDate.of(2023, JANUARY, 1);
        int expected = 1;
        int actual = date.getDayOfYear();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    static void testGetDayOfYearForDecember31st() {
        LocalDate date = LocalDate.of(2023, DECEMBER, 31);
        int expected = 365;
        int actual = date.getDayOfYear();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    static void testGetDayOfYearForLeapYear() {
        LocalDate date = LocalDate.of(2020, FEBRUARY, 29);
        int expected = 60;
        int actual = date.getDayOfYear();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    static void testGetDayOfYearForNonLeapYear() {
        LocalDate date = LocalDate.of(2019, FEBRUARY, 28);
        int expected = 59;
        int actual = date.getDayOfYear();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
}
