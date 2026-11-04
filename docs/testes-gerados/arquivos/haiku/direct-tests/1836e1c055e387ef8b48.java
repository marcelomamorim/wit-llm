/*
 * @test
 * @summary Tests DateTimeFormatter.ofPattern for formatting, parsing, literals and invalid patterns
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testFormatDate();
        testFormatTime();
        testParseRoundTrip();
        testQuotedLiteral();
        testInvalidPatterns();
    }

    static void testFormatDate() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        check("2020-01-15".equals(f.format(LocalDate.of(2020, 1, 15))), "yyyy-MM-dd format");
    }

    static void testFormatTime() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss");
        check("09:05:07".equals(f.format(LocalTime.of(9, 5, 7))), "HH:mm:ss format");
    }

    static void testParseRoundTrip() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate d = LocalDate.parse("2020-01-15", f);
        check(d.equals(LocalDate.of(2020, 1, 15)), "yyyy-MM-dd parse");
        check("2020-01-15".equals(f.format(d)), "round trip");
    }

    static void testQuotedLiteral() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("'Day' d");
        check("Day 5".equals(f.format(LocalDate.of(2020, 1, 5))), "quoted literal format");
    }

    static void testInvalidPatterns() {
        expectFailure("J", IllegalArgumentException.class);
        expectFailure("'abc", IllegalArgumentException.class);
        expectFailure(null, NullPointerException.class);
    }

    static void expectFailure(String pattern, Class<? extends Throwable> type) {
        try {
            DateTimeFormatter.ofPattern(pattern);
        } catch (Throwable t) {
            if (!type.isInstance(t)) {
                throw new RuntimeException("Unexpected exception for pattern " + pattern + ": " + t, t);
            }
            return;
        }
        throw new RuntimeException("Expected " + type.getName() + " for pattern " + pattern);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
