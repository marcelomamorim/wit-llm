/*
 * @test
 * @summary Verifies JulianFields.rangeRefinedBy returns the field range for supported temporals and throws DateTimeException otherwise
 * @run main JulianFieldsWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.JulianFields;
import java.time.temporal.ValueRange;

public class JulianFieldsWitupTest {

    public static void main(String[] args) {
        testSupportedDateReturnsRange();
        testSupportedDateSameForAllJulianFields();
        testUnsupportedTemporalThrows();
        System.out.println("All JulianFieldsWitupTest checks passed");
    }

    static void testSupportedDateReturnsRange() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        ValueRange expected = JulianFields.JULIAN_DAY.range();
        ValueRange actual = JulianFields.JULIAN_DAY.rangeRefinedBy(date);
        if (!expected.equals(actual)) {
            throw new RuntimeException("JULIAN_DAY rangeRefinedBy mismatch: expected "
                    + expected + " but got " + actual);
        }
    }

    static void testSupportedDateSameForAllJulianFields() {
        LocalDate date = LocalDate.of(1999, 12, 31);
        check(JulianFields.JULIAN_DAY, date);
        check(JulianFields.MODIFIED_JULIAN_DAY, date);
        check(JulianFields.RATA_DIE, date);
    }

    static void check(JulianFields.Field field, LocalDate date) {
        ValueRange expected = field.range();
        ValueRange actual = field.rangeRefinedBy(date);
        if (!expected.equals(actual)) {
            throw new RuntimeException("rangeRefinedBy mismatch for " + field
                    + ": expected " + expected + " but got " + actual);
        }
    }

    static void testUnsupportedTemporalThrows() {
        boolean thrown = false;
        try {
            JulianFields.JULIAN_DAY.rangeRefinedBy(LocalTime.of(10, 30));
        } catch (DateTimeException e) {
            thrown = true;
            if (e.getMessage() == null || !e.getMessage().startsWith("Unsupported field: ")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected DateTimeException for LocalTime");
        }
    }
}
