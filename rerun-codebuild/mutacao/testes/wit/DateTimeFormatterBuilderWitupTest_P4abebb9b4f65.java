/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.padNext(int, char) method.
 * @run main DateTimeFormatterBuilderWitupTest_P4abebb9b4f65
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P4abebb9b4f65 {

    public static void main(String[] args) {
        testPadNextWithValidInput();
        testPadNextWithInvalidInput();
    }

    private static void testPadNextWithValidInput() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(3, '*');
            System.out.println("testPadNextWithValidInput passed");
        } catch (Exception e) {
            System.err.println("testPadNextWithValidInput failed: " + e);
        }
    }

    private static void testPadNextWithInvalidInput() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(0, '*');
            System.err.println("testPadNextWithInvalidInput failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testPadNextWithInvalidInput passed");
        } catch (Exception e) {
            System.err.println("testPadNextWithInvalidInput failed: " + e);
        }
    }
}
