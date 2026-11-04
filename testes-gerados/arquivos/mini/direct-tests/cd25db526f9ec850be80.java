/*
 * @test
 * @summary Test appendOffsetId method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendOffsetIdProducesFormatter();
    }

    private static void testAppendOffsetIdProducesFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendOffsetId();
        DateTimeFormatter formatter = builder.toFormatter();

        // Test formatting of ZoneOffset with various offsets
        ZoneOffset offsetZero = ZoneOffset.UTC;
        ZoneOffset offsetPlus = ZoneOffset.ofHoursMinutes(5, 30);
        ZoneOffset offsetMinus = ZoneOffset.ofHours(-3);

        String formattedZero = formatter.format(offsetZero);
        String formattedPlus = formatter.format(offsetPlus);
        String formattedMinus = formatter.format(offsetMinus);

        // Expected strings are the standard offset IDs
        if (!"Z".equals(formattedZero)) {
            throw new AssertionError("Expected 'Z' for UTC offset, got: " + formattedZero);
        }
        if (!"+05:30".equals(formattedPlus)) {
            throw new AssertionError("Expected '+05:30' for offset +5:30, got: " + formattedPlus);
        }
        if (!"-03:00".equals(formattedMinus)) {
            throw new AssertionError("Expected '-03:00' for offset -3:00, got: " + formattedMinus);
        }

        // Test parsing of offset strings
        TemporalAccessor parsedZero = formatter.parse("Z");
        TemporalAccessor parsedPlus = formatter.parse("+05:30");
        TemporalAccessor parsedMinus = formatter.parse("-03:00");

        int parsedZeroSeconds = parsedZero.get(ChronoField.OFFSET_SECONDS);
        int parsedPlusSeconds = parsedPlus.get(ChronoField.OFFSET_SECONDS);
        int parsedMinusSeconds = parsedMinus.get(ChronoField.OFFSET_SECONDS);

        if (parsedZeroSeconds != 0) {
            throw new AssertionError("Expected 0 seconds offset for 'Z', got: " + parsedZeroSeconds);
        }
        if (parsedPlusSeconds != 19800) { // 5*3600 + 30*60
            throw new AssertionError("Expected 19800 seconds offset for '+05:30', got: " + parsedPlusSeconds);
        }
        if (parsedMinusSeconds != -10800) { // -3*3600
            throw new AssertionError("Expected -10800 seconds offset for '-03:00', got: " + parsedMinusSeconds);
        }
    }
}
