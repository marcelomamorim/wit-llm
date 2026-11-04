/*
 * @test
 * @summary Test java.time.ZoneId.of(String) for null input and valid input
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;

public class ZoneIdWitupTest {
    public static void main(String[] args) {
        testOfWithNull();
        testOfWithValidId();
    }

    private static void testOfWithNull() {
        try {
            ZoneId.of(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testOfWithValidId() {
        // Use a well-known zone ID
        ZoneId zone = ZoneId.of("Europe/Paris");
        if (zone == null) {
            throw new RuntimeException("ZoneId.of returned null for valid zone ID");
        }
        if (!"Europe/Paris".equals(zone.getId())) {
            throw new RuntimeException("ZoneId.of returned unexpected ID: " + zone.getId());
        }
    }
}
