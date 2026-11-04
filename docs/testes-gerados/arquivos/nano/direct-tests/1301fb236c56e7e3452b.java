/*
 * @test
 * @summary Test appendText method of DateTimeFormatterBuilder for various TemporalFields
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendTextWithChronoField();
        testAppendTextWithDifferentFields();
    }

    static void testAppendTextWithChronoField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.YEAR;
        builder.appendText(field);
        // Verify that the builder is not null and method returns builder instance
        if (builder == null) {
            throw new AssertionError("Builder should not be null after appendText");
        }
    }

    static void testAppendTextWithDifferentFields() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField[] fields = {
            ChronoField.MONTH_OF_YEAR,
            ChronoField.DAY_OF_MONTH,
            ChronoField.HOUR_OF_DAY,
            ChronoField.MINUTE_OF_HOUR,
            ChronoField.SECOND_OF_MINUTE,
            ChronoField.NANO_OF_SECOND,
            ChronoField.OFFSET_SECONDS,
            ChronoField.ERA
        };
        for (TemporalField field : fields) {
            builder.appendText(field);
            if (builder == null) {
                throw new AssertionError("Builder should not be null after appendText with field: " + field);
            }
        }
    }
}
