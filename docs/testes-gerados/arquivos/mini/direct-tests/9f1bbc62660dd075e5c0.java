/*
 * @test
 * @summary Test parseDefaulting method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testParseDefaultingReturnsSameBuilder();
        testParseDefaultingRejectsNullField();
    }

    private static void testParseDefaultingReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        long value = 15L;
        DateTimeFormatterBuilder returned = builder.parseDefaulting(field, value);
        if (returned != builder) {
            throw new AssertionError("parseDefaulting should return the same builder instance");
        }
    }

    private static void testParseDefaultingRejectsNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.parseDefaulting(null, 1L);
            throw new AssertionError("parseDefaulting should throw NullPointerException when field is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"field".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'field'");
            }
        }
    }
}
