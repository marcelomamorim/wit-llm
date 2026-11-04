/*
 * @test
 * @summary Tests for ZoneId.of(String, Map) method
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testOfWithAliasMap();
        testOfWithNullZoneId();
        testOfWithNullAliasMap();
        testOfWithUnknownZone();
    }

    static void testOfWithAliasMap() {
        Map<String, String> aliasMap = new HashMap<>();
        aliasMap.put("Paris", "Europe/Paris");
        ZoneId zoneId = ZoneId.of("Paris", aliasMap);
        assert zoneId != null : "ZoneId should not be null";
        assert zoneId.getId().equals("Europe/Paris") : "Expected 'Europe/Paris', got " + zoneId.getId();
    }

    static void testOfWithNullZoneId() {
        try {
            ZoneId.of(null, new HashMap<>());
            assert false : "Expected NullPointerException for null zoneId";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("zoneId") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testOfWithNullAliasMap() {
        try {
            ZoneId.of("Europe/Paris", null);
            assert false : "Expected NullPointerException for null aliasMap";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("aliasMap") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testOfWithUnknownZone() {
        Map<String, String> aliasMap = new HashMap<>();
        aliasMap.put("UnknownZone", "Invalid/Zone");
        try {
            ZoneId.of("UnknownZone", aliasMap);
            // Since the method does not validate the existence of the zone, it should succeed
            ZoneId zoneId = ZoneId.of("Invalid/Zone", aliasMap);
            assert zoneId != null : "ZoneId should not be null";
            assert zoneId.getId().equals("Invalid/Zone") : "Expected 'Invalid/Zone', got " + zoneId.getId();
        } catch (Exception e) {
            assert false : "No exception expected, but got: " + e;
        }
    }
}
