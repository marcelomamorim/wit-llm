/*
 * @test
 * @summary Basic tests for java.time.ZoneId.of(String) method
 * @run main java.time.ZoneIdWitupTest
 */

import java.time.ZoneId;

public class ZoneIdWitupTest {
    public static void main(String[] args) {
        testValidZoneId();
        testNullZoneId();
        testEmptyZoneId();
        testInvalidZoneId();
    }

    private static void testValidZoneId() {
        // Test with a known valid zone ID
        ZoneId zid = ZoneId.of("Europe/Paris");
        if (!"Europe/Paris".equals(zid.getId())) {
            throw new AssertionError("Expected Europe/Paris but got " + zid.getId());
        }

        // Test with UTC
        ZoneId utc = ZoneId.of("UTC");
        if (!"UTC".equals(utc.getId())) {
            throw new AssertionError("Expected UTC but got " + utc.getId());
        }
    }

    private static void testNullZoneId() {
        try {
            ZoneId.of(null);
            throw new AssertionError("Expected NullPointerException for null zoneId");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEmptyZoneId() {
        try {
            ZoneId.of("");
            throw new AssertionError("Expected DateTimeException for empty zoneId");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidZoneId() {
        try {
            ZoneId.of("Invalid/Zone");
            throw new AssertionError("Expected DateTimeException for invalid zoneId");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
