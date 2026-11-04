/*
 * @test
 * @summary Basic behavior of DateTimeFormatter.ofPattern(String, Locale)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNumericPatternFormatsDate();
        testMonthNameWithUsLocale();
        testDayNameWithUsLocale();
        testFormatterIsNotNull();
        testUnterminatedLiteralThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNumericPatternFormatsDate() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        String result = f.format(LocalDate.of(2020, 1, 15));
        check("2020-01-15".equals(result), "expected 2020-01-15 but got " + result);
    }

    static void testMonthNameWithUsLocale() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM", Locale.US);
        String result = f.format(LocalDate.of(2020, 1, 15));
        check("January".equals(result), "expected January but got " + result);
    }

    static void testDayNameWithUsLocale() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("EEEE", Locale.US);
        String result = f.format(LocalDate.of(2020, 1, 15));
        check("Wednesday".equals(result), "expected Wednesday but got " + result);
    }

    static void testFormatterIsNotNull() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy", Locale.US);
        check(f != null, "formatter must not be null");
    }

    static void testUnterminatedLiteralThrows() {
        boolean thrown = false;
        try {
            DateTimeFormatter.ofPattern("'unterminated", Locale.US);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for unterminated literal");
    }
}
