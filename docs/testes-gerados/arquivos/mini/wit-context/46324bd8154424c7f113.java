/*
 * @test
 * @summary Test java.time.ZoneId.of(String, Map<String,String>) for null checks and alias mapping
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ZoneIdWitupTest {
    public static void main(String[] args) {
        testNullZoneIdThrows();
        testNullAliasMapThrows();
        testAliasMapLookup();
        testAliasMapWithNoAliasReturnsOriginal();
    }

    private static void testNullZoneIdThrows() {
        try {
            ZoneId.of(null, new HashMap<String, String>());
            throw new AssertionError("Expected NullPointerException for null zoneId");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullAliasMapThrows() {
        try {
            ZoneId.of("Europe/Paris", null);
            throw new AssertionError("Expected NullPointerException for null aliasMap");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAliasMapLookup() {
        Map<String, String> aliasMap = new HashMap<String, String>();
        aliasMap.put("aliasZone", "Europe/Paris");
        ZoneId z = ZoneId.of("aliasZone", aliasMap);
        ZoneId expected = ZoneId.of("Europe/Paris");
        if (!z.equals(expected)) {
            throw new AssertionError("Alias mapping failed: expected " + expected + " but got " + z);
        }
    }

    private static void testAliasMapWithNoAliasReturnsOriginal() {
        Map<String, String> aliasMap = new HashMap<String, String>();
        aliasMap.put("someOtherZone", "Europe/Paris");
        ZoneId z = ZoneId.of("Europe/London", aliasMap);
        ZoneId expected = ZoneId.of("Europe/London");
        if (!z.equals(expected)) {
            throw new AssertionError("Expected original zoneId when no alias found: expected " + expected + " but got " + z);
        }
    }
}
