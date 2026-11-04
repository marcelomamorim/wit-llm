/*
 * @test
 * @summary Witup-style checks for MonthDay.parse(CharSequence, DateTimeFormatter)
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testParseValidMonthDay();
        testParseLeapDay();
        testParseNullFormatterThrowsNPE();
        testParseInvalidTextThrowsDateTimeParseException();
    }

    static void testParseValidMonthDay() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd");
        MonthDay md = MonthDay.parse("12-25", f);
        if (!md.equals(MonthDay.of(12, 25))) {
            throw new RuntimeException("Expected 12-25 but got " + md);
        }
    }

    static void testParseLeapDay() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd");
        MonthDay md = MonthDay.parse("02-29", f);
        if (!md.equals(MonthDay.of(2, 29))) {
            throw new RuntimeException("Expected 02-29 but got " + md);
        }
    }

    static void testParseNullFormatterThrowsNPE() {
        try {
            MonthDay.parse("12-25", null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(formatter, "formatter")
        }
    }

    static void testParseInvalidTextThrowsDateTimeParseException() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd");
        try {
            MonthDay.parse("xx-yy", f);
            throw new RuntimeException("Expected DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }
}
