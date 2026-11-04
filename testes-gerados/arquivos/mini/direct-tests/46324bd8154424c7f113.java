/*
 * @test
 * @summary Test java.time.ZoneId.of(String, Map) with alias map and null checks
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ZoneIdWitupTest {
    public static void main(String[] args) {
        testNullZoneId();
        testNullAliasMap();
        testAliasMapReturnsAlias();
        testAliasMapReturnsOriginalIfNoAlias();
    }

    private static void testNullZoneId() {
        try {
            ZoneId.of(null, new HashMap<String, String>());
            throw new RuntimeException("Expected NullPointerException for null zoneId");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullAliasMap() {
        try {
            ZoneId.of("Europe/Paris", null);
            throw new RuntimeException("Expected NullPointerException for null aliasMap");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAliasMapReturnsAlias() {
        Map<String, String> aliasMap = new HashMap<>();
        aliasMap.put("aliasZone", "Europe/Paris");
        ZoneId z = ZoneId.of("aliasZone", aliasMap);
        ZoneId expected = ZoneId.of("Europe/Paris");
        if (!z.equals(expected)) {
            throw new RuntimeException("Alias map did not return expected ZoneId");
        }
    }

    private static void testAliasMapReturnsOriginalIfNoAlias() {
        Map<String, String> aliasMap = new HashMap<>();
        aliasMap.put("someOtherZone", "Europe/Paris");
        ZoneId z = ZoneId.of("Europe/London", aliasMap);
        ZoneId expected = ZoneId.of("Europe/London");
        if (!z.equals(expected)) {
            throw new RuntimeException("ZoneId.of did not return original zoneId when no alias found");
        }
    }
}
