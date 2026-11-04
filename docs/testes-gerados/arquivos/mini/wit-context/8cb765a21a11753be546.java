/*
 * @test
 * @summary Test DateTimeFormatterBuilder.padNext(int) for valid and invalid padWidth
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
        System.out.println("All tests passed.");
    }

    private static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Valid padWidth values (>=1) should not throw and return non-null
        for (int padWidth = 1; padWidth <= 5; padWidth++) {
            DateTimeFormatterBuilder result = builder.padNext(padWidth);
            if (result == null) {
                throw new AssertionError("padNext(" + padWidth + ") returned null");
            }
            if (result != builder) {
                // The method returns a new builder or this, both acceptable, no spec on identity
                // Just ensure non-null
            }
        }
    }

    private static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // According to WIT expath and source, padWidth < 1 throws IllegalArgumentException
        int[] invalidValues = {0, -1, -10};
        for (int padWidth : invalidValues) {
            try {
                builder.padNext(padWidth);
                throw new AssertionError("Expected IllegalArgumentException for padWidth=" + padWidth);
            } catch (IllegalArgumentException expected) {
                // Expected exception
            } catch (Exception ex) {
                throw new AssertionError("Unexpected exception for padWidth=" + padWidth + ": " + ex);
            }
        }
    }
}
