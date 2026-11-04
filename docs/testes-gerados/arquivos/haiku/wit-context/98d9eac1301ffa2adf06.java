/*
 * @test
 * @summary Verifies JulianFields.rangeRefinedBy returns the field range for supported temporals and throws DateTimeException for unsupported ones
 * @run main JulianFieldsWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

public class JulianFieldsWitupTest {

    public static void main(String[] args) {
        testSupportedTemporalReturnsRange(JulianFields.JULIAN_DAY);
        testSupportedTemporalReturnsRange(JulianFields.MODIFIED_JULIAN_DAY);
        testSupportedTemporalReturnsRange(JulianFields.RATA_DIE);
        testUnsupportedTemporalThrows(JulianFields.JULIAN_DAY);
        testUnsupportedTemporalThrows(JulianFields.MODIFIED_JULIAN_DAY);
        testUnsupportedTemporalThrows(JulianFields.RATA_DIE);
    }

    private static void testSupportedTemporalReturnsRange(TemporalField field) {
        LocalDate date = LocalDate.of(2020, 1, 1);
        ValueRange expected = field.range();
        ValueRange actual = field.rangeRefinedBy(date);
        if (!expected.equals(actual)) {
            throw new RuntimeException("rangeRefinedBy mismatch for " + field
                    + ": expected " + expected + " but got " + actual);
        }
    }

    private static void testUnsupportedTemporalThrows(TemporalField field) {
        LocalTime time = LocalTime.of(10, 30);
        boolean thrown = false;
        try {
            field.rangeRefinedBy(time);
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected DateTimeException for unsupported temporal with " + field);
        }
    }
}
