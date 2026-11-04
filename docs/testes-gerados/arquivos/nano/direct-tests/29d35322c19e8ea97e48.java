/*
 * @test
 * @summary Test getVersions method of ZoneRulesProvider for various zone IDs
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.time.ZoneId;
import java.util.NavigableMap;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testGetVersionsWithValidZoneId();
        testGetVersionsWithNullZoneId();
        testGetVersionsWithInvalidZoneId();
    }

    static void testGetVersionsWithValidZoneId() {
        try {
            String zoneId = "America/New_York";
            NavigableMap<String, ZoneRulesProvider.ZoneRules> result = ZoneRulesProvider.getVersions(zoneId);
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid zone ID");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid zone ID: " + e);
        }
    }

    static void testGetVersionsWithNullZoneId() {
        try {
            ZoneRulesProvider.getVersions(null);
            throw new AssertionError("Expected NullPointerException for null zone ID");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null zone ID: " + e);
        }
    }

    static void testGetVersionsWithInvalidZoneId() {
        try {
            String invalidZoneId = "Invalid/Zone";
            NavigableMap<String, ZoneRulesProvider.ZoneRules> result = ZoneRulesProvider.getVersions(invalidZoneId);
            if (result == null) {
                throw new AssertionError("Expected non-null result for invalid zone ID");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid zone ID: " + e);
        }
    }
}
