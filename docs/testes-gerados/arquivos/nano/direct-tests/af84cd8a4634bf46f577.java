/*
 * @test
 * @summary Test appendValue method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendValueWithChronoField();
        testAppendValueWithNull();
    }

    static void testAppendValueWithChronoField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(ChronoField.YEAR);
            builder.appendValue(ChronoField.MONTH_OF_YEAR);
            builder.appendValue(ChronoField.DAY_OF_MONTH);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAppendValueWithChronoField", e);
        }
    }

    static void testAppendValueWithNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.appendValue(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to appendValue");
        }
    }
}
