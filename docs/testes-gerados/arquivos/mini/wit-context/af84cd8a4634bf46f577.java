/*
 * @test
 * @summary Test appendValue(TemporalField) method of DateTimeFormatterBuilder for null argument and normal usage
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendValueWithNullField();
        testAppendValueWithValidField();
    }

    private static void testAppendValueWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(null);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"field".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testAppendValueWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        DateTimeFormatterBuilder returned = builder.appendValue(field);
        if (returned != builder) {
            throw new AssertionError("appendValue should return the same builder instance");
        }
    }
}
