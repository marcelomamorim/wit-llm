/*
 * @test
 * @summary Test the format method of YearMonth with various formatters
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testFormatWithDefaultFormatter();
        testFormatWithCustomFormatter();
        testFormatWithNullFormatter();
    }

    static void testFormatWithDefaultFormatter() {
        YearMonth ym = YearMonth.of(2023, 3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM");
        String result = ym.format(formatter);
        assert result.equals("2023-03") : "Expected '2023-03' but got '" + result + "'";
    }

    static void testFormatWithCustomFormatter() {
        YearMonth ym = YearMonth.of(1999, 12);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 4, SignStyle.EXCEEDS_PAD)
            .appendLiteral("-")
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .toFormatter();
        String result = ym.format(formatter);
        assert result.equals("1999-12") : "Expected '1999-12' but got '" + result + "'";
    }

    static void testFormatWithNullFormatter() {
        YearMonth ym = YearMonth.of(2021, 7);
        try {
            ym.format(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
