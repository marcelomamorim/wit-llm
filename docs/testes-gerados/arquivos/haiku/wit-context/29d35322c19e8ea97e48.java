/*
 * @test
 * @summary Witup-derived tests for ZoneRulesProvider.getVersions null handling and basic public behavior
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.NavigableMap;

public class ZoneRulesProviderWitupTest {
    public static void main(String[] args) {
        boolean npeThrown = false;
        try {
            ZoneRulesProvider.getVersions(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null zoneId");
        }

        NavigableMap<String, ZoneRules> versions = ZoneRulesProvider.getVersions("Europe/London");
        if (versions == null) {
            throw new AssertionError("getVersions returned null for a valid zone id");
        }
        if (versions.isEmpty()) {
            throw new AssertionError("getVersions returned an empty map for a valid zone id");
        }
    }
}
