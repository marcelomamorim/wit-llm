/*
 * @test
 * @summary Test getRules method of ZoneRulesProvider for various zone IDs
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testGetRulesWithValidZoneId();
        testGetRulesWithNullZoneId();
        testGetRulesWithInvalidZoneId();
    }

    static void testGetRulesWithValidZoneId() {
        String zoneId = "America/New_York";
        try {
            ZoneRules rules = ZoneRulesProvider.getRules(zoneId, false);
            if (rules == null) {
                throw new AssertionError("Expected non-null ZoneRules for valid zone ID") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid zone ID", e);
        }
    }

    static void testGetRulesWithNullZoneId() {
        try {
            ZoneRulesProvider.getRules(null, false);
            throw new AssertionError("Expected NullPointerException for null zoneId");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null zoneId", e);
        }
    }

    static void testGetRulesWithInvalidZoneId() {
        String invalidZoneId = "Invalid/Zone";
        try {
            ZoneRules rules = ZoneRulesProvider.getRules(invalidZoneId, false);
            if (rules != null) {
                throw new AssertionError("Expected null or exception for invalid zone ID");
            }
        } catch (Exception e) {
            // Depending on implementation, may throw exception or return null
            // Acceptable: do nothing
        }
    }
}
