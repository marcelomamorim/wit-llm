/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.appendText method, focusing on null argument handling.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendText_NullField();
        testAppendText_NullTextStyle();
    }

    private static void testAppendText_NullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(null, TextStyle.FULL);
            System.err.println("Failed: Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null field");
        }
    }

    private static void testAppendText_NullTextStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            // Using a dummy TemporalField, e.g., ChronoField.YEAR
            builder.appendText(java.time.temporal.ChronoField.YEAR, null);
            System.err.println("Failed: Expected NullPointerException for null textStyle");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null textStyle");
        }
    }
}
