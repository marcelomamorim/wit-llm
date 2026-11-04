/*
 * @test
 * @summary Basic tests for DateTimeFormatterBuilder.appendText(TemporalField)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.format.TextStyle;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.LocalDate;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendTextWithValidField();
    }

    private static void testAppendTextWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // appendText(field) delegates to appendText(field, TextStyle.FULL)
        builder.appendText(ChronoField.MONTH_OF_YEAR);
        DateTimeFormatter formatter = builder.toFormatter();

        // Format a date with month to check if text is output
        LocalDate date = LocalDate.of(2018, 9, 25);
        String result = formatter.format(date);

        // The result should be the full month name (e.g. September)
        if (!result.equals("September")) {
            throw new AssertionError("Expected 'September' but got '" + result + "'");
        }
    }
}
