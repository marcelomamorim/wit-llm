/*
 * @test
 * @summary Witup-generated tests for LocalDateTime.format(DateTimeFormatter)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testIsoLocalDateTimeFormat();
        testCustomPatternFormat();
        testFractionalSecondsFormat();
        testMidnightFormat();
    }

    static void testNullFormatterThrowsNPE() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
        try {
            dt.format(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null formatter");
    }

    static void testIsoLocalDateTimeFormat() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
        check("2020-01-02T03:04:05", dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    static void testCustomPatternFormat() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        check("2020-01-02 03:04:05", dt.format(f));
    }

    static void testFractionalSecondsFormat() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 123000000);
        check("2020-01-02T03:04:05.123", dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    static void testMidnightFormat() {
        LocalDateTime dt = LocalDateTime.of(1999, 12, 31, 0, 0);
        check("1999-12-31T00:00", dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
