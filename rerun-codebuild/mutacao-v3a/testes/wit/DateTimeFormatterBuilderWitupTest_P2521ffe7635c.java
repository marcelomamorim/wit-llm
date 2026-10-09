/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendLocalized method.
 * @run main DateTimeFormatterBuilderWitupTest_P2521ffe7635c
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_P2521ffe7635c {

    public static void main(String[] args) {
        testAppendLocalized_NullDateStyle();
        testAppendLocalized_NullTimeStyle();
        testAppendLocalized_BothNonNull();
    }

    static void testAppendLocalized_NullDateStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, FormatStyle.SHORT);
            System.out.println("testAppendLocalized_NullDateStyle: FAILED - exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testAppendLocalized_NullDateStyle: PASSED");
        } catch (Throwable t) {
            System.out.println("testAppendLocalized_NullDateStyle: FAILED - wrong exception: " + t);
        }
    }

    static void testAppendLocalized_NullTimeStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(FormatStyle.MEDIUM, null);
            System.out.println("testAppendLocalized_NullTimeStyle: PASSED");
        } catch (Throwable t) {
            System.out.println("testAppendLocalized_NullTimeStyle: FAILED - exception: " + t);
        }
    }

    static void testAppendLocalized_BothNonNull() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(FormatStyle.FULL, FormatStyle.SHORT);
            System.out.println("testAppendLocalized_BothNonNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testAppendLocalized_BothNonNull: FAILED - exception: " + t);
        }
    }
}
