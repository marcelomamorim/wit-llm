/*
 * @test
 * @summary Witup-generated checks for OffsetTime.format(DateTimeFormatter)
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testFormatWithOffset();
        testFormatWithZeroOffset();
    }

    static void testNullFormatterThrowsNPE() {
        OffsetTime ot = OffsetTime.of(10, 30, 15, 0, ZoneOffset.ofHours(2));
        boolean thrown = false;
        try {
            ot.format(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "format(null) should throw NullPointerException");
    }

    static void testFormatWithOffset() {
        OffsetTime ot = OffsetTime.of(10, 30, 15, 0, ZoneOffset.ofHours(2));
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss XXX");
        String s = ot.format(f);
        check("10:30:15 +02:00".equals(s), "unexpected format result: " + s);
    }

    static void testFormatWithZeroOffset() {
        OffsetTime ot = OffsetTime.of(LocalTime.of(23, 59), ZoneOffset.UTC);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm XXX");
        String s = ot.format(f);
        check("23:59 Z".equals(s), "unexpected format result: " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
