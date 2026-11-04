/*
 * @test
 * @summary Verifies DateTimeFormatterBuilder.appendText(TemporalField, Map) formatting, builder chaining, map copy semantics and null checks
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
        testFormatsMappedText();
        testReturnsSameBuilder();
        testCopiesLookupMap();
        testNullField();
        testNullTextLookup();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFormatsMappedText() {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "Mon");
        map.put(2L, "Tue");
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_WEEK, map)
                .toFormatter();
        // 2018-09-03 is a Monday, 2018-09-04 is a Tuesday
        check("Mon".equals(f.format(LocalDate.of(2018, 9, 3))), "expected Mon");
        check("Tue".equals(f.format(LocalDate.of(2018, 9, 4))), "expected Tue");
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "Mon");
        check(b.appendText(ChronoField.DAY_OF_WEEK, map) == b, "appendText must return this builder");
    }

    static void testCopiesLookupMap() {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "Mon");
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_WEEK, map);
        // mutate the caller's map after appendText; the builder must have copied it
        map.put(1L, "Changed");
        DateTimeFormatter f = b.toFormatter();
        check("Mon".equals(f.format(LocalDate.of(2018, 9, 3))), "builder must keep copy of lookup map");
    }

    static void testNullField() {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "Mon");
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(null, map);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null field must throw NullPointerException");
    }

    static void testNullTextLookup() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_WEEK, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null textLookup must throw NullPointerException");
    }
}
