/*
 * @test
 * @summary Test DateTimeFormatterBuilder.padNext method behavior
 * @run main DateTimeFormatterBuilderWitupTest_Pa4a4835edaee
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pa4a4835edaee {
    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
    }

    private static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Test with padWidth = 1 and padChar = '*'
        DateTimeFormatterBuilder result = builder.padNext(1, '*');
        if (result != builder) {
            throw new AssertionError("padNext should return this instance");
        }
        // Test with padWidth = 5 and padChar = '0'
        builder.padNext(5, '0');
    }

    private static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(0, '*');
            throw new AssertionError("Expected IllegalArgumentException for padWidth=0");
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("pad width must be at least one")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
        try {
            builder.padNext(-1, '*');
            throw new AssertionError("Expected IllegalArgumentException for padWidth=-1");
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("pad width must be at least one")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
