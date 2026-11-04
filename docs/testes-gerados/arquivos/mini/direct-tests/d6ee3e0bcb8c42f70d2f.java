/*
 * @test
 * @summary Test java.time.LocalDate.of(int,int,int) for valid and boundary values
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testValidDate();
        testInvalidMonth();
        testInvalidDay();
        testInvalidYear();
    }

    private static void testValidDate() {
        LocalDate date = LocalDate.of(2018, 9, 30);
        if (date.getYear() != 2018 || date.getMonthValue() != 9 || date.getDayOfMonth() != 30) {
            throw new AssertionError("Valid date fields do not match");
        }
    }

    private static void testInvalidMonth() {
        try {
            LocalDate.of(2018, 0, 15);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDate.of(2018, 13, 15);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidDay() {
        try {
            LocalDate.of(2018, 1, 0);
            throw new AssertionError("Expected DateTimeException for day=0");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDate.of(2018, 1, 32);
            throw new AssertionError("Expected DateTimeException for day=32");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidYear() {
        try {
            LocalDate.of(-1000000000, 1, 1);
            throw new AssertionError("Expected DateTimeException for year out of range");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDate.of(1000000000, 1, 1);
            throw new AssertionError("Expected DateTimeException for year out of range");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
