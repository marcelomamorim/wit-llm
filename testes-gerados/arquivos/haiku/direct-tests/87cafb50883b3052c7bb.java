/*
 * @test
 * @summary Tests LocalDateTime.format(DateTimeFormatter) for null rejection and observable formatted output
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(message + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    static void testNullFormatterThrowsNPE() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 15, 10, 30);
        boolean thrown = false;
        try {
            dt.format(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "format(null) must throw NullPointerException");
    }

    static void testIsoLocalDateTimeWithoutSeconds() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 15, 10, 30);
        checkEquals("2024-01-15T10:30:00",
                dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "ISO_LOCAL_DATE_TIME format");
    }

    static void testIsoLocalDateTimeWithNanos() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 15, 10, 30, 5, 123000000);
        checkEquals("2024-01-15T10:30:05.123",
                dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "ISO_LOCAL_DATE_TIME with millis");
    }

    static void testCustomPattern() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 15, 10, 30);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm");
        checkEquals("15/01/2024 10:30", dt.format(f), "custom pattern format");
    }

    static void testPatternWithLiteralT() {
        LocalDateTime dt = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
        checkEquals("2024-12-31T23:59:59", dt.format(f), "pattern with quoted literal");
    }

    static void testFormatIsRepeatable() {
        LocalDateTime dt = LocalDateTime.of(2000, 2, 29, 0, 0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");
        String first = dt.format(f);
        String second = dt.format(f);
        checkEquals(first, second, "repeated format must be deterministic");
        checkEquals("2000/02/29 00:00", first, "leap day format");
    }

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testIsoLocalDateTimeWithoutSeconds();
        testIsoLocalDateTimeWithNanos();
        testCustomPattern();
        testPatternWithLiteralT();
        testFormatIsRepeatable();
    }
}
