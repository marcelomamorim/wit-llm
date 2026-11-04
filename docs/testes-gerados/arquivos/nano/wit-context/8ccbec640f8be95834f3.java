/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendZoneText(TextStyle)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendZoneTextWithNull();
        testAppendZoneTextWithValidStyle();
    }

    private static void testAppendZoneTextWithNull() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendZoneText(null);
            System.out.println("FAIL: Expected NullPointerException for null TextStyle");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null TextStyle");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAppendZoneTextWithValidStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendZoneText(TextStyle.SHORT);
            System.out.println("PASS: appendZoneText with valid TextStyle executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
