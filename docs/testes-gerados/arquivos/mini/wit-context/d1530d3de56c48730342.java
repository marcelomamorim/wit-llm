/*
 * @test
 * @summary Test DateTimeFormatterBuilder.padNext(int,char) for valid and invalid padWidth
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
    }

    private static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Test with valid padWidth and padChar
        DateTimeFormatterBuilder result = builder.padNext(1, '*');
        if (result != builder) {
            throw new AssertionError("padNext should return this instance");
        }
        // Test with larger padWidth
        builder.padNext(5, '#');
    }

    private static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(0, '*');
            throw new AssertionError("Expected IllegalArgumentException for padWidth=0");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
        try {
            builder.padNext(-1, '*');
            throw new AssertionError("Expected IllegalArgumentException for padWidth=-1");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
