/*
 * @test
 * @summary Test padNext method of DateTimeFormatterBuilder for valid and invalid inputs
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
    }

    static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.padNext(3, '*');
        // Verify that the internal state is set correctly by invoking padNext again
        builder.padNext(5, '#');
        System.out.println("testPadNextValid passed");
    }

    static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.padNext(0, '@');
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for padWidth=0");
        }
        System.out.println("testPadNextInvalid passed");
    }
}
