/*
 * @test
 * @summary Test parseDefaulting method for null input
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testParseDefaultingWithNullField();
        testParseDefaultingWithValidField();
    }

    private static void testParseDefaultingWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.parseDefaulting(null, 1L);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when field is null");
        }
    }

    private static void testParseDefaultingWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Using a standard TemporalField from ChronoField
        TemporalField field = java.time.temporal.ChronoField.YEAR;
        builder.parseDefaulting(field, 2023L);
        // No exception expected, test passes if no exception thrown
    }
}
