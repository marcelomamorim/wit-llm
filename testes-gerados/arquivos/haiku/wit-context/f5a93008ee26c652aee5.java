/*
 * @test
 * @summary Witup-generated checks for LocalDate.parse(CharSequence, DateTimeFormatter)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidIsoParse();
        testValidPatternParse();
        testNullFormatterThrowsNpe();
        testNullTextThrowsNpe();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidIsoParse() {
        LocalDate date = LocalDate.parse("2018-09-15", DateTimeFormatter.ISO_LOCAL_DATE);
        check(date.equals(LocalDate.of(2018, 9, 15)), "ISO parse returned unexpected date: " + date);
    }

    static void testValidPatternParse() {
        LocalDate date = LocalDate.parse("15/09/2018", DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        check(date.equals(LocalDate.of(2018, 9, 15)), "pattern parse returned unexpected date: " + date);
    }

    static void testNullFormatterThrowsNpe() {
        try {
            LocalDate.parse("2018-09-15", null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null formatter");
    }

    static void testNullTextThrowsNpe() {
        try {
            LocalDate.parse(null, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null text");
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        try {
            LocalDate.parse("not-a-date", DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException expected) {
            return;
        }
        throw new RuntimeException("expected DateTimeParseException for invalid text");
    }
}
