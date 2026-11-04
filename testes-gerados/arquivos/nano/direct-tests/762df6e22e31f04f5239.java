/*
 * @test
 * @summary Test YearMonth.parse(CharSequence, DateTimeFormatter)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testParseWithFormatter();
    }

    public static void testParseWithFormatter() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .toFormatter();

        YearMonth ym = YearMonth.parse("2023-07", formatter);
        assert ym.getYear() == 2023 : "Expected year 2023, got " + ym.getYear();
        assert ym.getMonthValue() == 7 : "Expected month 7, got " + ym.getMonthValue();

        YearMonth ym2 = YearMonth.parse("1999-12", formatter);
        assert ym2.getYear() == 1999 : "Expected year 1999, got " + ym2.getYear();
        assert ym2.getMonthValue() == 12 : "Expected month 12, got " + ym2.getMonthValue();
    }
}
