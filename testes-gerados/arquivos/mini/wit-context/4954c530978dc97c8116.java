/*
 * @test
 * @summary Test appendText(TemporalField, Map<Long,String>) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendTextNullField();
        testAppendTextNullTextLookup();
        testAppendTextValid();
    }

    private static void testAppendTextNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(null, Collections.singletonMap(Long.valueOf(1L), "One"));
            throw new RuntimeException("Expected NullPointerException for null field");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAppendTextNullTextLookup() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(ChronoField.DAY_OF_MONTH, null);
            throw new RuntimeException("Expected NullPointerException for null textLookup");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAppendTextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> textLookup = new HashMap<>();
        textLookup.put(Long.valueOf(1L), "One");
        textLookup.put(Long.valueOf(2L), "Two");
        DateTimeFormatterBuilder result = builder.appendText(ChronoField.DAY_OF_MONTH, textLookup);
        if (result == null) {
            throw new RuntimeException("appendText returned null");
        }
        if (result != builder) {
            throw new RuntimeException("appendText did not return this instance");
        }
    }
}
