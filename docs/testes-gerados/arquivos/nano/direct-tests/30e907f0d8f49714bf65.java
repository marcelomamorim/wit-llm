/*
 * @test    @summary Test ZoneId.of(String) with valid and invalid inputs
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testValidZoneId();
        testNullZoneId();
        testEmptyZoneId();
        testInvalidZoneId();
    }

    static void testValidZoneId() {
        String validZone = "Europe/Paris";
        ZoneId zone = ZoneId.of(validZone);
        if (!zone.getId().equals(validZone)) {
            throw new AssertionError("Expected zone id: " + validZone + ", but got: " + zone.getId());
        }
    }

    static void testNullZoneId() {
        try {
            ZoneId.of(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyZoneId() {
        try {
            ZoneId.of("");
            throw new AssertionError("Expected ZoneRulesException for empty string");
        } catch (java.time.zone.ZoneRulesException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testInvalidZoneId() {
        String invalidZone = "Invalid/Zone";
        try {
            ZoneId.of(invalidZone);
            throw new AssertionError("Expected ZoneRulesException for invalid zone id");
        } catch (java.time.zone.ZoneRulesException expected) {
            // expected
        }
    }
}
