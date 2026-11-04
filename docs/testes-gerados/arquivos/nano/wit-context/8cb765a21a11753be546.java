/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.padNext(int)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testPadNextWithValidInput();
        testPadNextWithInvalidInput();
    }

    private static void testPadNextWithValidInput() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(3);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid input", e);
        }
    }

    private static void testPadNextWithInvalidInput() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.padNext(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown for padNext(0)");
        }
    }
}
