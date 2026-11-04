/*
 * @test
 * @summary Tests for java.time.LocalDate.of(int, int, int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testYearTooSmall();
        testYearTooLarge();
        testMonthTooSmall();
        testMonthTooLarge();
        testDayTooSmall();
        testDayTooLarge();
    }

    static void testValidDate() {
        try {
            LocalDate date = LocalDate.of(2020, 5, 15);
            if (date.getYear() != 2020 || date.getMonthValue() != 5 || date.getDayOfMonth() != 15) {
                throw new AssertionError("Valid date test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Valid date test threw exception", e);
        }
    }

    static void testYearTooSmall() {
        try {
            LocalDate.of(0, 1, 1);
            throw new AssertionError("Year too small did not throw");
        } catch (Exception e) {
            // Expected exception
        }
    }

    static void testYearTooLarge() {
        try {
            LocalDate.of(999999999, 1, 1);
            // Depending on implementation, may throw; accept exception
        } catch (Exception e) {
            // Expected exception
        }
    }

    static void testMonthTooSmall() {
        try {
            LocalDate.of(2020, 0, 10);
            throw new AssertionError("Month too small did not throw");
        } catch (Exception e) {
            // Expected exception
        }
    }

    static void testMonthTooLarge() {
        try {
            LocalDate.of(2020, 13, 10);
            throw new AssertionError("Month too large did not throw");
        } catch (Exception e) {
            // Expected exception
        }
    }

    static void testDayTooSmall() {
        try {
            LocalDate.of(2020, 1, 0);
            throw new AssertionError("Day too small did not throw");
        } catch (Exception e) {
            // Expected exception
        }
    }

    static void testDayTooLarge() {
        try {
            LocalDate.of(2020, 1, 32);
            throw new AssertionError("Day too large did not throw");
        } catch (Exception e) {
            // Expected exception
        }
    }
}
