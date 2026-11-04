/*
 * @test
 * @summary Checks DateTimeFormatter.toFormat(TemporalQuery) null handling and observable parse/format behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullParseQueryThrowsNpe();
        testFormatAndParseWithQuery();
        testInvalidTextThrowsParseException();
    }

    static void testNullParseQueryThrowsNpe() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.toFormat(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testFormatAndParseWithQuery() throws ParseException {
        TemporalQuery<LocalDate> query = LocalDate::from;
        Format format = DateTimeFormatter.ISO_LOCAL_DATE.toFormat(query);
        if (format == null) {
            throw new AssertionError("toFormat returned null");
        }
        LocalDate date = LocalDate.of(2018, 9, 1);
        String text = format.format(date);
        if (!"2018-09-01".equals(text)) {
            throw new AssertionError("unexpected format result: " + text);
        }
        Object parsed = format.parseObject("2018-09-01");
        if (!date.equals(parsed)) {
            throw new AssertionError("unexpected parse result: " + parsed);
        }
    }

    static void testInvalidTextThrowsParseException() {
        TemporalQuery<LocalDate> query = LocalDate::from;
        Format format = DateTimeFormatter.ISO_LOCAL_DATE.toFormat(query);
        try {
            format.parseObject("2018-09-0x");
            throw new AssertionError("expected ParseException");
        } catch (ParseException expected) {
        }
    }
}
