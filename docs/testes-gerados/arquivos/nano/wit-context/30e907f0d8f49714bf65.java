/*
 * @test
 * @summary Tests for java.time.ZoneId.of(String)
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testNullZoneId();
        testValidZoneId();
    }

    public static void testNullZoneId() {
        try {
            ZoneId.of(null);
            System.err.println("Expected NullPointerException for null zoneId");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testValidZoneId() {
        String validZone = "Europe/Paris";
        ZoneId zid = ZoneId.of(validZone);
        if (zid == null || !zid.getId().equals(validZone)) {
            System.err.println("Failed to create ZoneId with valid zone string");
            System.exit(1);
        }
    }
}
