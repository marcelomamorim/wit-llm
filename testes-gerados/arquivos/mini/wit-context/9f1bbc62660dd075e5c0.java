/*
 * @test
 * @summary Test parseDefaulting method of DateTimeFormatterBuilder for null field and normal usage
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testParseDefaultingNullField();
        testParseDefaultingNormal();
        System.out.println("All tests passed.");
    }

    private static void testParseDefaultingNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.parseDefaulting(null, 1L);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"field".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testParseDefaultingNormal() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Use a known TemporalField constant from ChronoField
        TemporalField field = java.time.temporal.ChronoField.DAY_OF_MONTH;
        long value = 15L;
        DateTimeFormatterBuilder result = builder.parseDefaulting(field, value);
        if (result != builder) {
            throw new AssertionError("parseDefaulting should return this instance");
        }
        // No exception expected, no direct observable state to assert
    }
}
