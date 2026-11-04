/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendText method
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;
import java.util.Map;
import java.util.HashMap;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendTextWithValidMap();
        testAppendTextWithNullField();
        testAppendTextWithNullMap();
    }

    static void testAppendTextWithValidMap() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> textLookup = new HashMap<>();
        textLookup.put(1L, "One");
        textLookup.put(2L, "Two");
        try {
            builder.appendText(ChronoField.MONTH_OF_YEAR, textLookup);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAppendTextWithValidMap", e);
        }
    }

    static void testAppendTextWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> textLookup = new HashMap<>();
        textLookup.put(1L, "One");
        try {
            builder.appendText(null, textLookup);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAppendTextWithNullMap() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(ChronoField.MONTH_OF_YEAR, null);
            throw new AssertionError("Expected NullPointerException for null map");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
