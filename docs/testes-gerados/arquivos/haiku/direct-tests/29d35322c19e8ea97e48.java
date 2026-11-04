/*
 * @test
 * @summary Basic observable behavior of ZoneRulesProvider.getVersions
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.Map;
import java.util.NavigableMap;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullZoneIdThrowsNpeWithMessage();
        testKnownZoneReturnsNonEmptyVersions();
        testRepeatedCallsReturnSameVersionKeys();
    }

    static void testNullZoneIdThrowsNpeWithMessage() {
        try {
            ZoneRulesProvider.getVersions(null);
        } catch (NullPointerException e) {
            if (!"zoneId".equals(e.getMessage())) {
                throw new RuntimeException("unexpected NPE message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected NullPointerException for null zoneId");
    }

    static void testKnownZoneReturnsNonEmptyVersions() {
        NavigableMap<String, ZoneRules> versions = ZoneRulesProvider.getVersions("Europe/Paris");
        if (versions == null) {
            throw new RuntimeException("getVersions returned null");
        }
        if (versions.isEmpty()) {
            throw new RuntimeException("getVersions returned an empty map for Europe/Paris");
        }
        for (Map.Entry<String, ZoneRules> e : versions.entrySet()) {
            if (e.getKey() == null || e.getValue() == null) {
                throw new RuntimeException("null key or rules value in versions map");
            }
        }
    }

    static void testRepeatedCallsReturnSameVersionKeys() {
        NavigableMap<String, ZoneRules> first = ZoneRulesProvider.getVersions("Europe/Paris");
        NavigableMap<String, ZoneRules> second = ZoneRulesProvider.getVersions("Europe/Paris");
        if (!first.keySet().equals(second.keySet())) {
            throw new RuntimeException("version keys differ between repeated calls: "
                    + first.keySet() + " vs " + second.keySet());
        }
    }
}
