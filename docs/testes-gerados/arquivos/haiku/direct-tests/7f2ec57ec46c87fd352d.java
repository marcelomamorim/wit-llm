/*
 * @test
 * @summary Tests OffsetDateTime.ofInstant for null checks, offset resolution, nanosecond preservation and named zones
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testEpochInUtc();
        testFixedOffsetZone();
        testNanosPreserved();
        testNamedZoneParis();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullInstant() {
        try {
            OffsetDateTime.ofInstant(null, ZoneOffset.UTC);
            throw new RuntimeException("expected NullPointerException for null instant");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullZone() {
        try {
            OffsetDateTime.ofInstant(Instant.EPOCH, null);
            throw new RuntimeException("expected NullPointerException for null zone");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEpochInUtc() {
        OffsetDateTime result = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
        check(result.equals(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)),
              "epoch in UTC should be 1970-01-01T00:00Z but was " + result);
        check(result.toInstant().equals(Instant.EPOCH), "toInstant should round-trip epoch");
    }

    static void testFixedOffsetZone() {
        ZoneOffset plusTwo = ZoneOffset.ofHours(2);
        OffsetDateTime result = OffsetDateTime.ofInstant(Instant.EPOCH, plusTwo);
        check(result.getOffset().equals(plusTwo), "offset should be +02:00 but was " + result.getOffset());
        check(result.getHour() == 2, "hour should be 2 but was " + result.getHour());
        check(result.getDayOfMonth() == 1, "day should be 1 but was " + result.getDayOfMonth());
    }

    static void testNanosPreserved() {
        Instant instant = Instant.ofEpochSecond(1516017600L, 500L);
        OffsetDateTime result = OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
        check(result.getNano() == 500, "nano should be 500 but was " + result.getNano());
        check(result.getHour() == 12, "hour should be 12 but was " + result.getHour());
        check(result.toInstant().equals(instant), "toInstant should preserve the instant");
    }

    static void testNamedZoneParis() {
        Instant instant = Instant.ofEpochSecond(1516017600L);
        OffsetDateTime result = OffsetDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
        check(result.getOffset().equals(ZoneOffset.ofHours(1)),
              "Paris offset in January should be +01:00 but was " + result.getOffset());
        check(result.getHour() == 13, "Paris local hour should be 13 but was " + result.getHour());
        check(result.toInstant().equals(instant), "toInstant should preserve the instant");
    }
}
