/*
 * @test
 * @summary Tests YearMonth.parse(CharSequence, DateTimeFormatter)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("uuuu-MM");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter reversedFormatter = DateTimeFormatter.ofPattern("MM/uuuu");

        check(YearMonth.of(2012, 6).equals(YearMonth.parse("2012-06", yearMonthFormatter)),
              "basic parse with uuuu-MM failed");

        check(YearMonth.of(2012, 6).equals(YearMonth.parse(new StringBuilder("2012-06"), yearMonthFormatter)),
              "parse with StringBuilder input failed");

        check(YearMonth.of(2012, 6).equals(YearMonth.parse("2012-06-15", dayFormatter)),
              "parse ignoring day-of-month failed");

        check(YearMonth.of(2012, 6).equals(YearMonth.parse("06/2012", reversedFormatter)),
              "parse with MM/uuuu pattern failed");

        check(YearMonth.of(1999, 12).equals(YearMonth.parse("1999-12", yearMonthFormatter)),
              "parse of end-of-year month failed");

        try {
            YearMonth.parse("2012-06", null);
            throw new RuntimeException("expected NullPointerException for null formatter");
        } catch (NullPointerException expected) {
            // expected
        }

        try {
            YearMonth.parse("2012/06", yearMonthFormatter);
            throw new RuntimeException("expected DateTimeParseException for mismatched text");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
