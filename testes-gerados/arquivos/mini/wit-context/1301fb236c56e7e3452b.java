/*
 * @test
 * @summary Test java.time.format.DateTimeFormatterBuilder.appendText(TemporalField) for null and normal input
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendTextWithNullField();
        testAppendTextWithValidField();
    }

    private static void testAppendTextWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(null);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAppendTextWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Use a valid TemporalField from ChronoField
        TemporalField field = ChronoField.DAY_OF_MONTH;
        DateTimeFormatterBuilder result = builder.appendText(field);
        if (result == null) {
            throw new AssertionError("appendText returned null for valid field");
        }
        if (result == builder) {
            // It is allowed to return this for chaining, so no error
        } else {
            // No further checks possible without internal state access
        }
    }
}
