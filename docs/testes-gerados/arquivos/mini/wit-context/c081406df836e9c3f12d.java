/*
 * @test
 * @summary Test java.time.ZonedDateTime.ofInstant(Instant, ZoneId) for null argument checks and normal behavior
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testValidInstantAndZone();
    }

    private static void testNullInstant() {
        try {
            ZonedDateTime.ofInstant(null, ZoneId.of("UTC"));
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            if (!"instant".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null instant");
            }
        }
    }

    private static void testNullZone() {
        Instant now = Instant.now();
        try {
            ZonedDateTime.ofInstant(now, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            if (!"zone".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null zone");
            }
        }
    }

    private static void testValidInstantAndZone() {
        Instant instant = Instant.ofEpochSecond(1_000_000_000L, 123456789);
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zone);
        if (zdt == null) {
            throw new AssertionError("ZonedDateTime.ofInstant returned null for valid inputs");
        }
        if (zdt.getZone() != zone) {
            throw new AssertionError("ZoneId mismatch");
        }
        if (zdt.toInstant().equals(instant) == false) {
            throw new AssertionError("Instant mismatch after conversion");
        }
    }
}
