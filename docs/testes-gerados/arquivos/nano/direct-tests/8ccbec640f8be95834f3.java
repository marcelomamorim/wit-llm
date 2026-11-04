/*
 * @test
 * @summary Test appendZoneText method of DateTimeFormatterBuilder for different TextStyle inputs.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendZoneTextWithFullStyle();
        testAppendZoneTextWithShortStyle();
    }

    static void testAppendZoneTextWithFullStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendZoneText(TextStyle.FULL);
        String result = builder.toString();
        if (result == null || result.isEmpty()) {
            throw new AssertionError("appendZoneText with FULL style resulted in empty string");
        }
    }

    static void testAppendZoneTextWithShortStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendZoneText(TextStyle.SHORT);
        String result = builder.toString();
        if (result == null || result.isEmpty()) {
            throw new AssertionError("appendZoneText with SHORT style resulted in empty string");
        }
    }
}
