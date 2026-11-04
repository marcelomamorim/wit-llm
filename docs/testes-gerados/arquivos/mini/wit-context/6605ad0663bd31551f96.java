/*
 * @test
 * @summary Test ZoneRulesProvider.getRules(String, boolean) for null input and basic behavior
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {
    public static void main(String[] args) {
        testGetRulesNullZoneId();
        testGetRulesNonNullZoneId();
    }

    private static void testGetRulesNullZoneId() {
        try {
            ZoneRulesProvider.getRules(null, true);
            throw new AssertionError("Expected NullPointerException for null zoneId");
        } catch (NullPointerException npe) {
            if (!"zoneId".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testGetRulesNonNullZoneId() {
        // Use a known ZoneId string that should be supported by the system providers
        // We cannot instantiate ZoneRulesProvider directly, so we rely on the system default providers
        String zoneId = "UTC";
        ZoneRules rules = ZoneRulesProvider.getRules(zoneId, false);
        if (rules == null) {
            throw new AssertionError("ZoneRulesProvider.getRules returned null for zoneId 'UTC'");
        }
    }
}
