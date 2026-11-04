/*
 * @test
 * @summary Verify observable behavior of Instant.parse for ISO-8601 UTC instant text
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class InstantWitupTest {

    public static void main(String[] args) {
        testEpochZero();
        testWholeSecondsParse();
        testFractionalNanosParse();
        testRoundTripWithMicros();
        testMissingZoneDesignatorRejected();
        testEmptyTextRejected();
    }

    static void testEpochZero() {
        Instant i = Instant.parse("1970-01-01T00:00:00Z");
        check(i.getEpochSecond() == 0L, "epoch second of epoch zero");
        check(i.getNano() == 0, "nano of epoch zero");
    }

    static void testWholeSecondsParse() {
        Instant i = Instant.parse("2011-12-03T10:15:30Z");
        check(i.getEpochSecond() == 1322907330L, "epoch second of 2011-12-03T10:15:30Z");
        check(i.getNano() == 0, "nano of whole-second instant");
    }

    static void testFractionalNanosParse() {
        Instant i = Instant.parse("1970-01-01T00:00:00.123456789Z");
        check(i.getEpochSecond() == 0L, "epoch second with fractional part");
        check(i.getNano() == 123456789, "nano with nine fractional digits");
    }

    static void testRoundTripWithMicros() {
        Instant original = Instant.ofEpochSecond(1322907330L, 5000L);
        Instant parsed = Instant.parse(original.toString());
        check(original.equals(parsed), "round trip through toString and parse");
    }

    static void testMissingZoneDesignatorRejected() {
        try {
            Instant.parse("2011-12-03T10:15:30");
            throw new RuntimeException("expected DateTimeParseException for missing 'Z'");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void testEmptyTextRejected() {
        try {
            Instant.parse("");
            throw new RuntimeException("expected DateTimeParseException for empty text");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
