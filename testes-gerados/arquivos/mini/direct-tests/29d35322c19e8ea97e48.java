/*
 * @test
 * @summary Test java.time.zone.ZoneRulesProvider.getVersions(String) method
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.time.zone.ZoneRules;
import java.util.NavigableMap;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetVersionsNullZoneId();
        testGetVersionsKnownZoneId();
    }

    private static void testGetVersionsNullZoneId() {
        try {
            ZoneRulesProvider.getVersions(null);
            throw new AssertionError("Expected NullPointerException for null zoneId");
        } catch (NullPointerException npe) {
            // expected
            if (!"zoneId".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testGetVersionsKnownZoneId() {
        // Use a known ZoneId that should be supported by the system providers
        // For example, "UTC" is always available
        String zoneId = "UTC";
        NavigableMap<String, ZoneRules> versions = ZoneRulesProvider.getVersions(zoneId);
        if (versions == null) {
            throw new AssertionError("getVersions returned null for zoneId: " + zoneId);
        }
        if (versions.isEmpty()) {
            throw new AssertionError("getVersions returned empty map for zoneId: " + zoneId);
        }
        for (String versionKey : versions.keySet()) {
            Objects.requireNonNull(versionKey, "version key");
            ZoneRules rules = versions.get(versionKey);
            if (rules == null) {
                throw new AssertionError("ZoneRules is null for version: " + versionKey);
            }
        }
    }
}
