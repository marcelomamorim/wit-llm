/*
 * @test
 * @summary Tests OffsetTime.parse null-argument handling and a basic successful parse
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testNullTextThrowsNPE();
        testValidParse();
    }

    static void testNullFormatterThrowsNPE() {
        try {
            OffsetTime.parse("10:15:30+01:00", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null formatter");
    }

    static void testNullTextThrowsNPE() {
        try {
            OffsetTime.parse(null, DateTimeFormatter.ISO_OFFSET_TIME);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null text");
    }

    static void testValidParse() {
        OffsetTime expected = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(1));
        OffsetTime actual = OffsetTime.parse("10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_TIME);
        if (!expected.equals(actual)) {
            throw new RuntimeException("unexpected parse result: " + actual);
        }
    }
}
