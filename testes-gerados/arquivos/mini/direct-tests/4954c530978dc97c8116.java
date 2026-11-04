/*
 * @test
 * @summary Test appendText(TemporalField, Map<Long,String>) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testAppendTextBasic();
        testAppendTextWithEmptyMap();
        testAppendTextWithMultipleEntries();
    }

    private static void testAppendTextBasic() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> map = new LinkedHashMap<>();
        map.put(1L, "One");
        builder.appendText(ChronoField.DAY_OF_MONTH, map);
        // No exception means success
    }

    private static void testAppendTextWithEmptyMap() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> map = Collections.emptyMap();
        builder.appendText(ChronoField.MONTH_OF_YEAR, map);
        // No exception means success
    }

    private static void testAppendTextWithMultipleEntries() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> map = new LinkedHashMap<>();
        map.put(0L, "Zero");
        map.put(1L, "One");
        map.put(2L, "Two");
        builder.appendText(ChronoField.HOUR_OF_DAY, map);
        // No exception means success
    }
}