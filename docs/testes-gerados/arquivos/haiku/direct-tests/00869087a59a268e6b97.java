/*
 * @test
 * @summary MonthDay.format(DateTimeFormatter) delegates to the formatter and rejects null formatter
 * @run main MonthDayWitupTest
 */

import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class MonthDayWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNumericPatternFormatting() {
        MonthDay md = MonthDay.of(2, 29);
        String result = md.format(DateTimeFormatter.ofPattern("MM-dd"));
        check("02-29".equals(result), "expected 02-29 but was " + result);
    }

    static void testSingleDigitPatternFormatting() {
        MonthDay md = MonthDay.of(1, 5);
        String result = md.format(DateTimeFormatter.ofPattern("d/M"));
        check("5/1".equals(result), "expected 5/1 but was " + result);
    }

    static void testMonthEnumFormatting() {
        MonthDay md = MonthDay.of(java.time.Month.DECEMBER, 1);
        String result = md.format(DateTimeFormatter.ofPattern("MM/dd"));
        check("12/01".equals(result), "expected 12/01 but was " + result);
    }

    static void testLiteralPatternFormatting() {
        MonthDay md = MonthDay.of(7, 4);
        String result = md.format(DateTimeFormatter.ofPattern("'--'MM-dd"));
        check("--07-04".equals(result), "expected --07-04 but was " + result);
    }

    static void testNullFormatterThrows() {
        MonthDay md = MonthDay.of(3, 15);
        try {
            md.format(null);
            throw new RuntimeException("expected NullPointerException for null formatter");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(formatter, "formatter")
        }
    }

    static void testUnsupportedFieldThrows() {
        MonthDay md = MonthDay.of(3, 15);
        try {
            md.format(DateTimeFormatter.ofPattern("HH"));
            throw new RuntimeException("expected DateTimeException for unsupported field");
        } catch (DateTimeException expected) {
            // expected: MonthDay does not support HOUR_OF_DAY
        }
    }

    public static void main(String[] args) {
        testNumericPatternFormatting();
        testSingleDigitPatternFormatting();
        testMonthEnumFormatting();
        testLiteralPatternFormatting();
        testNullFormatterThrows();
        testUnsupportedFieldThrows();
    }
}
