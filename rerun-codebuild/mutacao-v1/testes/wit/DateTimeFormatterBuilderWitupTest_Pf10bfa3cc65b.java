/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.padNext(int, char) method, focusing on exception path when padWidth < 1.
 * @run main DateTimeFormatterBuilderWitupTest_Pf10bfa3cc65b
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pf10bfa3cc65b {

    public static void main(String[] args) {
        testPadNextWithInvalidPadWidth();
    }

    public static void testPadNextWithInvalidPadWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(0, ' ');
            throw new AssertionError("Expected IllegalArgumentException was not thrown for padWidth=0");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("The pad width must be at least one")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
        try {
            builder.padNext(-5, ' ');
            throw new AssertionError("Expected IllegalArgumentException was not thrown for padWidth=-5");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("The pad width must be at least one")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
    }
}
