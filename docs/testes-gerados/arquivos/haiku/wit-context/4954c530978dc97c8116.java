/*
 * @test
 * @summary Witup-derived checks for DateTimeFormatterBuilder.appendText null handling and text lookup copy semantics
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullField();
        testNullTextLookup();
        testFluentReturnAndFormatting();
        testLookupIsCopied();
    }

    static void testNullField() {
        Map<Long, String> lookup = new HashMap<>();
        lookup.put(1L, "Mon");
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(null, lookup);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null field");
        }
    }

    static void testNullTextLookup() {
        Map<Long, String> nullMap = null;
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_WEEK, nullMap);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null textLookup");
        }
    }

    static void testFluentReturnAndFormatting() {
        Map<Long, String> lookup = new HashMap<>();
        lookup.put(1L, "Mon");
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder returned = builder.appendText(ChronoField.DAY_OF_WEEK, lookup);
        if (returned != builder) {
            throw new RuntimeException("appendText must return this builder");
        }
        DateTimeFormatter formatter = builder.toFormatter();
        String text = formatter.format(LocalDate.of(2018, 9, 17));
        if (!"Mon".equals(text)) {
            throw new RuntimeException("expected 'Mon' but got '" + text + "'");
        }
    }

    static void testLookupIsCopied() {
        Map<Long, String> lookup = new HashMap<>();
        lookup.put(1L, "Mon");
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_WEEK, lookup);
        lookup.put(1L, "Changed");
        DateTimeFormatter formatter = builder.toFormatter();
        String text = formatter.format(LocalDate.of(2018, 9, 17));
        if (!"Mon".equals(text)) {
            throw new RuntimeException("lookup was not copied at call time, got '" + text + "'");
        }
    }
}
