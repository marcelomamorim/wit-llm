/*
 * @test
 * @summary Verify DateTimeFormatterBuilder.appendText(TemporalField) returns the same builder and appends full text for the field
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder returned = builder.appendText(ChronoField.DAY_OF_WEEK);
        if (returned != builder) {
            throw new RuntimeException("appendText must return the same builder instance");
        }

        DateTimeFormatter dowFormatter = returned.toFormatter().withLocale(Locale.US);
        String dow = dowFormatter.format(LocalDate.of(2018, 9, 17));
        if (!"Monday".equals(dow)) {
            throw new RuntimeException("Expected 'Monday' but got '" + dow + "'");
        }

        DateTimeFormatter monthFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR)
                .toFormatter()
                .withLocale(Locale.US);
        String month = monthFormatter.format(LocalDate.of(2018, 9, 17));
        if (!"September".equals(month)) {
            throw new RuntimeException("Expected 'September' but got '" + month + "'");
        }
    }
}
