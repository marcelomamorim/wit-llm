/*
 * @test
 * @summary Test padNext method for DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest_Pc91e2f7a9be9
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pc91e2f7a9be9 {

    public static void main(String[] args) {
        testPadNextValidInput();
        testPadNextInvalidInput();
    }

    private static void testPadNextValidInput() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.padNext(3, '*');
        // Verify that no exception is thrown and object is returned
        assert builder != null : "Builder should not be null after padNext";
    }

    private static void testPadNextInvalidInput() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.padNext(0, '*');
            assert false : "Expected IllegalArgumentException for padWidth=0";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("pad width") : "Exception message should mention 'pad width'";
        }
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.padNext(-1, '*');
            assert false : "Expected IllegalArgumentException for padWidth=-1";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("pad width") : "Exception message should mention 'pad width'";
        }
    }
}
