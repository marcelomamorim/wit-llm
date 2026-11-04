/*
 * @test
 * @summary Tests DateTimeFormatter.toFormat(TemporalQuery) classic Format behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TemporalQuery<LocalDate> query = LocalDate::from;

        Format format = formatter.toFormat(query);
        if (format == null) {
            throw new RuntimeException("toFormat returned null");
        }

        String text = format.format(LocalDate.of(2020, 1, 15));
        if (!"2020-01-15".equals(text)) {
            throw new RuntimeException("unexpected formatted text: " + text);
        }

        Object parsed = format.parseObject("2020-01-15");
        if (!LocalDate.of(2020, 1, 15).equals(parsed)) {
            throw new RuntimeException("unexpected parsed value: " + parsed);
        }

        boolean npe = false;
        try {
            formatter.toFormat(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null query");
        }

        boolean parseFailed = false;
        try {
            format.parseObject("xx");
        } catch (ParseException e) {
            parseFailed = true;
        }
        if (!parseFailed) {
            throw new RuntimeException("expected ParseException for invalid text");
        }
    }
}
