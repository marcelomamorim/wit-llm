/*
 * @test
 * @summary Test appendValue(TemporalField) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendValueWithValidField();
        testAppendValueWithNullField();
    }

    private static void testAppendValueWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        DateTimeFormatterBuilder returned = builder.appendValue(field);
        if (returned != builder) {
            throw new AssertionError("appendValue should return this instance");
        }
    }

    private static void testAppendValueWithNullField() {
        try {
            new DateTimeFormatterBuilder().appendValue(null);
            throw new AssertionError("appendValue(null) should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
            if (!"field".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'field'");
            }
        }
    }
}
