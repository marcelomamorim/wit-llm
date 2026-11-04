/*
 * @test
 * @summary Tests for java.time.LocalDate.withYear(int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithYearSameYear();
        testWithYearValid();
        testWithYearInvalid();
    }

    static void testWithYearSameYear() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalDate result = date.withYear(2020);
        if (result != date) {
            throw new AssertionError("Expected same instance when year is unchanged");
        }
    }

    static void testWithYearValid() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalDate result = date.withYear(2021);
        if (result.getYear() != 2021) {
            throw new AssertionError("Year not updated correctly");
        }
        if (result.getMonthValue() != date.getMonthValue() || result.getDayOfMonth() != date.getDayOfMonth()) {
            throw new AssertionError("Month or day changed unexpectedly");
        }
    }

    static void testWithYearInvalid() {
        try {
            LocalDate.of(2020, 1, 1).withYear(Integer.MIN_VALUE);
            throw new AssertionError("Expected DateTimeException for invalid year");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDate.of(2020, 1, 1).withYear(Integer.MAX_VALUE);
            // Depending on implementation, may throw or succeed; assume throw for test
            throw new AssertionError("Expected DateTimeException for invalid year");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
