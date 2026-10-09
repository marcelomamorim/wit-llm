/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.padNext(int, char)
 * @run main DateTimeFormatterBuilderWitupTest_Pc91e2f7a9be9
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_Pc91e2f7a9be9 {

    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
    }

    static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(3, '*');
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid padWidth", e);
        }
    }

    static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(0, '*');
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("at least one")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
