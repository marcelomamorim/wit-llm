/*
 * @test
 * @summary Test DateTimeFormatterBuilder.appendPattern(String) for null input and basic usage
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendPatternNull();
        testAppendPatternValid();
    }

    private static void testAppendPatternNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendPattern(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"pattern".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testAppendPatternValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Use a simple valid pattern
        DateTimeFormatterBuilder returned = builder.appendPattern("yyyy-MM-dd");
        if (returned != builder) {
            throw new AssertionError("appendPattern should return this");
        }
    }
}
