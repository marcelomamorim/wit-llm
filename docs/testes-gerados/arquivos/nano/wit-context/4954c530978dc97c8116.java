/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendText method.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.util.Map;
import java.util.HashMap;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendTextNullField();
        testAppendTextNullTextLookup();
        testAppendTextValidInputs();
    }

    private static void testAppendTextNullField() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendText(null, new HashMap<Long, String>());
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAppendTextNullTextLookup() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendText(java.time.temporal.ChronoField.YEAR, null);
            throw new AssertionError("Expected NullPointerException for null textLookup");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAppendTextValidInputs() {
        try {
            Map<Long, String> textLookup = new HashMap<>();
            textLookup.put(1L, "One");
            textLookup.put(2L, "Two");
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendText(java.time.temporal.ChronoField.MONTH_OF_YEAR, textLookup);
            // No exception expected, test passes if no exception thrown
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
