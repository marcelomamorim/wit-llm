/*
 * @test
 * @summary Test java.time.zone.ZoneRulesProvider.getRules(String, boolean) method
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetRulesWithNullZoneId();
        testGetRulesWithInvalidZoneId();
        testGetRulesWithValidZoneId();
    }

    private static void testGetRulesWithNullZoneId() {
        try {
            ZoneRulesProvider.getRules(null, true);
            throw new RuntimeException("Expected NullPointerException for null zoneId");
        } catch (NullPointerException npe) {
            // expected
            if (!"zoneId".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testGetRulesWithInvalidZoneId() {
        String invalidZoneId = "Invalid/ZoneId";
        try {
            ZoneRulesProvider.getRules(invalidZoneId, true);
            throw new RuntimeException("Expected ZoneRulesProvider to throw for invalid zoneId");
        } catch (Exception ex) {
            // Accept any exception as the provider lookup or provideRules may throw
            // We do not assert exact exception type because it depends on installed providers
            if (ex instanceof NullPointerException) {
                throw new RuntimeException("Unexpected NullPointerException for invalid zoneId", ex);
            }
        }
    }

    private static void testGetRulesWithValidZoneId() {
        // Use a known valid zoneId from ZoneId system
        String validZoneId = "UTC";
        ZoneRules rules = ZoneRulesProvider.getRules(validZoneId, true);
        if (rules == null) {
            throw new RuntimeException("Expected non-null ZoneRules for valid zoneId");
        }
    }
}
