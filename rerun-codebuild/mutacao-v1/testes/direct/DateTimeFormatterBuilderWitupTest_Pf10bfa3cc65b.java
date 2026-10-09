/*
 * @test
 * @summary Test DateTimeFormatterBuilder.padNext method for correct behavior and exception handling
 * @run main DateTimeFormatterBuilderWitupTest_Pf10bfa3cc65b
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pf10bfa3cc65b {

    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
    }

    private static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(3, '*');
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid padNext", e);
        }
    }

    private static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.padNext(0, '*');
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for padNext with width 0")
        }
    }
}
