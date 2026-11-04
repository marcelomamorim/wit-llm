/*
 * @test
 * @summary LocalTime.format(DateTimeFormatter) formats the time and rejects a null formatter
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testFormatBasicPattern();
        testFormatMidnight();
        testFormatWithNanos();
        testFormatNullFormatterThrowsNPE();
        System.out.println("PASSED");
    }

    static void testFormatBasicPattern() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss");
        String s = LocalTime.of(9, 5, 7).format(f);
        check("09:05:07".equals(s), "expected 09:05:07 but was " + s);
    }

    static void testFormatMidnight() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm");
        String s = LocalTime.MIDNIGHT.format(f);
        check("00:00".equals(s), "expected 00:00 but was " + s);
    }

    static void testFormatWithNanos() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
        String s = LocalTime.of(23, 59, 59, 999999999).format(f);
        check("23:59:59.999999999".equals(s), "expected 23:59:59.999999999 but was " + s);
    }

    static void testFormatNullFormatterThrowsNPE() {
        boolean thrown = false;
        try {
            LocalTime.of(10, 15).format(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null formatter");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
