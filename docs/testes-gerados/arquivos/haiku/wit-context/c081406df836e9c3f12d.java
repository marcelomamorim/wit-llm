/*
 * @test
 * @summary Checks ZonedDateTime.ofInstant public behavior: valid conversion preserves the instant and zone, and null arguments raise NullPointerException.
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInstantPreservesInstantAndZone();
        testNegativeEpochWithNanosIsPreserved();
        testNullInstantThrowsNPE();
        testNullZoneThrowsNPE();
        testBothNullThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidInstantPreservesInstantAndZone() {
        Instant instant = Instant.ofEpochSecond(0L, 5L);
        ZoneId zone = ZoneOffset.UTC;
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zone);
        check(zdt != null, "result must not be null");
        check(instant.equals(zdt.toInstant()), "toInstant must equal the input instant");
        check(zone.equals(zdt.getZone()), "getZone must equal the input zone");
    }

    static void testNegativeEpochWithNanosIsPreserved() {
        Instant instant = Instant.ofEpochSecond(-1L, 999_999_999L);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        check(instant.equals(zdt.toInstant()), "negative epoch second with nanos must be preserved");
        check(zdt.getNano() == 999_999_999, "nano of second must be preserved");
    }

    static void testNullInstantThrowsNPE() {
        boolean thrown = false;
        try {
            ZonedDateTime.ofInstant(null, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null instant must throw NullPointerException");
    }

    static void testNullZoneThrowsNPE() {
        boolean thrown = false;
        try {
            ZonedDateTime.ofInstant(Instant.EPOCH, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null zone must throw NullPointerException");
    }

    static void testBothNullThrowsNPE() {
        boolean thrown = false;
        try {
            ZonedDateTime.ofInstant(null, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null instant and zone must throw NullPointerException");
    }
}
