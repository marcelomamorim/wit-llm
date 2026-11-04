/*
 * @test
 * @summary Tests the withMonth method of LocalDate for various scenarios.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import static java.time.LocalDate.of;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithMonthSameMonth();
        testWithMonthValidChange();
        testWithMonthInvalidValue();
    }

    static void testWithMonthSameMonth() {
        LocalDate date = of(2021, 5, 15);
        LocalDate result = date.withMonth(5);
        if (result != date) {
            throw new AssertionError("Expected same instance when setting same month");
        }
    }

    static void testWithMonthValidChange() {
        LocalDate date = of(2021, 5, 15);
        LocalDate result = date.withMonth(6);
        if (result.getYear() != 2021 || result.getMonthValue() != 6 || result.getDayOfMonth() != 15) {
            throw new AssertionError("Failed to change month correctly");
        }
    }

    static void testWithMonthInvalidValue() {
        LocalDate date = of(2021, 5, 15);
        try {
            date.withMonth(0);
            throw new AssertionError("Expected exception for invalid month 0");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            date.withMonth(13);
            throw new AssertionError("Expected exception for invalid month 13");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
