/*
 * @test
 * @summary Test padNext method of DateTimeFormatterBuilder for various pad widths.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testPadNextWithPositiveWidth();
        testPadNextWithZeroWidth();
        testPadNextWithLargeWidth();
    }

    static void testPadNextWithPositiveWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        int padWidth = 5;
        DateTimeFormatterBuilder result = builder.padNext(padWidth);
        if (result != builder) {
            throw new AssertionError("padNext should return the same builder instance") ;
        }
        // No direct observable effect, test that method completes without exception
    }

    static void testPadNextWithZeroWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        int padWidth = 0;
        DateTimeFormatterBuilder result = builder.padNext(padWidth);
        if (result != builder) {
            throw new AssertionError("padNext with zero width should return the same builder instance") ;
        }
    }

    static void testPadNextWithLargeWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        int padWidth = 1000;
        DateTimeFormatterBuilder result = builder.padNext(padWidth);
        if (result != builder) {
            throw new AssertionError("padNext with large width should return the same builder instance") ;
        }
    }
}
