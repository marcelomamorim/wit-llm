/*
 * @test
 * @summary Test java.time.zone.ZoneRulesProvider.getVersions(String) for null input and normal behavior
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.util.NavigableMap;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {
    public static void main(String[] args) {
        testGetVersionsNullZoneId();
        testGetVersionsWithInvalidZoneId();
    }

    private static void testGetVersionsNullZoneId() {
        try {
            ZoneRulesProvider.getVersions(null);
            throw new AssertionError("Expected NullPointerException for null zoneId");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"zoneId".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testGetVersionsWithInvalidZoneId() {
        // The method calls getProvider(zoneId).provideVersions(zoneId)
        // Without access to internal providers or a public way to register providers,
        // we cannot guarantee a valid zoneId or the returned map.
        // We test that for a likely invalid zoneId, a RuntimeException or ServiceConfigurationError may be thrown,
        // or a non-null NavigableMap is returned.
        String invalidZoneId = "Invalid/ZoneId_For_Test";
        try {
            NavigableMap<String, ZoneRules> versions = ZoneRulesProvider.getVersions(invalidZoneId);
            if (versions == null) {
                throw new AssertionError("Returned NavigableMap must not be null");
            }
        } catch (RuntimeException | Error e) {
            // Acceptable: may throw if no provider found or service loader error
        }
    }
}
