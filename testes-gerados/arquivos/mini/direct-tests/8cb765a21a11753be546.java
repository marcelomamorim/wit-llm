/*
 * @test
 * @summary Test DateTimeFormatterBuilder.padNext(int) method
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();

        // Test with positive padWidth
        DateTimeFormatterBuilder result = builder.padNext(5);
        if (result == null) {
            throw new AssertionError("padNext(5) returned null");
        }

        // Test with padWidth = 0
        result = builder.padNext(0);
        if (result == null) {
            throw new AssertionError("padNext(0) returned null");
        }

        // Test with negative padWidth
        result = builder.padNext(-1);
        if (result == null) {
            throw new AssertionError("padNext(-1) returned null");
        }

        System.out.println("All tests passed.");
    }
}
