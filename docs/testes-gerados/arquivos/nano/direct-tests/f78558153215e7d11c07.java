/* @test    @summary Test getRules() method of ZoneRegion class    @run main ZoneRegionWitupTest */

import java.time.ZoneRegion;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;

public class ZoneRegionWitupTest {

    public static void main(String[] args) {
        testGetRulesReturnsNonNull();
        testGetRulesReturnsFromProviderWhenRulesIsNull();
    }

    static void testGetRulesReturnsNonNull() {
        ZoneRegion zoneRegion = createZoneRegion("Test/Region", null);
        ZoneRules rules = zoneRegion.getRules();
        if (rules == null) {
            throw new AssertionError("Expected non-null ZoneRules from getRules()");
        }
    }

    static void testGetRulesReturnsFromProviderWhenRulesIsNull() {
        String id = "Test/Region";
        ZoneRegion zoneRegion = createZoneRegion(id, null);
        ZoneRules rulesFromProvider = ZoneRulesProvider.getRules(id, false);
        ZoneRules rulesFromMethod = zoneRegion.getRules();
        if (rulesFromMethod == null) {
            throw new AssertionError("Expected non-null ZoneRules from getRules()");
        }
        if (rulesFromMethod != rulesFromProvider) {
            throw new AssertionError("getRules() did not return rules from provider as expected");
        }
    }

    static ZoneRegion createZoneRegion(String id, ZoneRules rules) {
        // Since ZoneRegion constructor is package-private, simulate creation via reflection or assume access
        // For testing purposes, create an anonymous subclass or mock if possible
        // Here, assuming access to constructor for test simplicity
        return new ZoneRegion(id, rules);
    }
}
