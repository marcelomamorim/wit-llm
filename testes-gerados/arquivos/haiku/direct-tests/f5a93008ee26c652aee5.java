/*
 * @test
 * @summary Tests LocalDate.parse(CharSequence, DateTimeFormatter) observable behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testIsoParse();
        testCustomPattern();
        testCharSequenceImplementation();
        testNullFormatterThrowsNpe();
        testInvalidDayOfMonthThrowsParseException();
        testTrailingTextThrowsParseException();
    }

    static void testIsoParse() {
        LocalDate actual = LocalDate.parse("2012-02-29", DateTimeFormatter.ISO_LOCAL_DATE);
        check(LocalDate.of(2012, 2, 29).equals(actual), "ISO parse of leap day failed: " + actual);
    }

    static void testCustomPattern() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate actual = LocalDate.parse("31/01/2020", f);
        check(LocalDate.of(2020, 1, 31).equals(actual), "custom pattern parse failed: " + actual);
    }

    static void testCharSequenceImplementation() {
        StringBuilder sb = new StringBuilder("2010-05-06");
        LocalDate actual = LocalDate.parse(sb, DateTimeFormatter.ISO_LOCAL_DATE);
        check(LocalDate.of(2010, 5, 6).equals(actual), "StringBuilder parse failed: " + actual);
    }

    static void testNullFormatterThrowsNpe() {
        boolean thrown = false;
        try {
            LocalDate.parse("2012-02-29", null);
        } catch (NullPointerException e) {
            thrown = true;
            check("formatter".equals(e.getMessage()),
                  "unexpected NPE message: " + e.getMessage());
        }
        check(thrown, "null formatter did not throw NullPointerException");
    }

    static void testInvalidDayOfMonthThrowsParseException() {
        boolean thrown = false;
        try {
            LocalDate.parse("2021-02-30", DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "invalid date 2021-02-30 was accepted");
    }

    static void testTrailingTextThrowsParseException() {
        boolean thrown = false;
        try {
            LocalDate.parse("2020-01-01T10:00", DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "text with trailing time was accepted by ISO_LOCAL_DATE");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
