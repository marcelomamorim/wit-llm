/*
 * @test
 * @summary Behavioral tests for LocalDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testParseIsoLocalDateTime();
        testParseWithCustomPattern();
        testParseWithNanoseconds();
        testParseAcceptsStringBuilder();
        testNullFormatterThrowsNPE();
        testUnparseableTextThrowsDateTimeParseException();
        testNullTextThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testParseIsoLocalDateTime() {
        LocalDateTime actual = LocalDateTime.parse("2020-01-02T03:04:05",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        check(actual.equals(LocalDateTime.of(2020, 1, 2, 3, 4, 5)),
                "ISO parse mismatch: " + actual);
    }

    static void testParseWithCustomPattern() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm");
        LocalDateTime actual = LocalDateTime.parse("05/03/2021 14:30", f);
        check(actual.equals(LocalDateTime.of(2021, 3, 5, 14, 30)),
                "custom pattern parse mismatch: " + actual);
    }

    static void testParseWithNanoseconds() {
        LocalDateTime actual = LocalDateTime.parse("2020-01-02T03:04:05.123456789",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        check(actual.getNano() == 123456789,
                "nano mismatch: " + actual.getNano());
        check(actual.equals(LocalDateTime.of(2020, 1, 2, 3, 4, 5, 123456789)),
                "nanos parse mismatch: " + actual);
    }

    static void testParseAcceptsStringBuilder() {
        StringBuilder sb = new StringBuilder("2000-12-31T23:59:00");
        LocalDateTime actual = LocalDateTime.parse(sb, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        check(actual.equals(LocalDateTime.of(2000, 12, 31, 23, 59, 0)),
                "CharSequence parse mismatch: " + actual);
    }

    static void testNullFormatterThrowsNPE() {
        boolean thrown = false;
        try {
            LocalDateTime.parse("2020-01-02T03:04:05", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null formatter");
    }

    static void testUnparseableTextThrowsDateTimeParseException() {
        boolean thrown = false;
        try {
            LocalDateTime.parse("not-a-date", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeParseException for invalid text");
    }

    static void testNullTextThrowsNPE() {
        boolean thrown = false;
        try {
            LocalDateTime.parse(null, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null text");
    }
}
