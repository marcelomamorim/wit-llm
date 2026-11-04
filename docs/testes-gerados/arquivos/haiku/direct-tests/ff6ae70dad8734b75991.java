/*
 * @test
 * @summary Year.parse(CharSequence, DateTimeFormatter) delegates to the formatter, rejects a null formatter and propagates parse failures
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearWitupTest {

    public static void main(String[] args) {
        testParseYearPattern();
        testParseWithYearMonthPattern();
        testNullFormatterThrowsNPE();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void testParseYearPattern() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu");
        Year y = Year.parse("2012", f);
        check(y.getValue() == 2012, "expected 2012 but got " + y.getValue());

        Year y2 = Year.parse("1999", f);
        check(y2.getValue() == 1999, "expected 1999 but got " + y2.getValue());
    }

    static void testParseWithYearMonthPattern() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM");
        Year y = Year.parse("2012-06", f);
        check(y.getValue() == 2012, "expected 2012 but got " + y.getValue());
    }

    static void testNullFormatterThrowsNPE() {
        boolean thrown = false;
        try {
            Year.parse("2012", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null formatter");
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu");
        boolean thrown = false;
        try {
            Year.parse("abc", f);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeParseException for invalid text");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
