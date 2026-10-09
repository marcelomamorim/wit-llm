/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendLocalized method.
 * @run main DateTimeFormatterBuilderWitupTest_P1d6477880d49
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_P1d6477880d49 {

    public static void main(String[] args) {
        testAppendLocalizedWithNullDateStyle();
        testAppendLocalizedWithNullTimeStyle();
        testAppendLocalizedWithBothNull();
        testAppendLocalizedWithValidStyles();
    }

    private static void testAppendLocalizedWithNullDateStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, FormatStyle.SHORT);
            System.out.println("FAIL: Expected IllegalArgumentException for null dateStyle");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null dateStyle");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testAppendLocalizedWithNullTimeStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(FormatStyle.MEDIUM, null);
            System.out.println("PASS: Allowed null timeStyle");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null timeStyle: " + e);
        }
    }

    private static void testAppendLocalizedWithBothNull() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, null);
            System.out.println("FAIL: Expected IllegalArgumentException for both null styles");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for both null styles");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testAppendLocalizedWithValidStyles() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(FormatStyle.FULL, FormatStyle.SHORT);
            System.out.println("PASS: appendLocalized with valid styles succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid styles: " + e);
        }
    }
}
