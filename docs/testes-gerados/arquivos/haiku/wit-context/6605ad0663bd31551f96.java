/*
 * @test
 * @summary Witup-generated checks for ZoneRulesProvider.getRules(String, boolean) null handling and valid zone lookup
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testNullZoneIdThrowsNpeForCachingFalse();
        testNullZoneIdThrowsNpeForCachingTrue();
        testValidZoneReturnsRules();
        System.out.println("PASSED");
    }

    static void testNullZoneIdThrowsNpeForCachingFalse() {
        try {
            ZoneRulesProvider.getRules(null, false);
            throw new AssertionError("Expected NullPointerException for null zoneId (forCaching=false)");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullZoneIdThrowsNpeForCachingTrue() {
        try {
            ZoneRulesProvider.getRules(null, true);
            throw new AssertionError("Expected NullPointerException for null zoneId (forCaching=true)");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testValidZoneReturnsRules() {
        ZoneRules rules = ZoneRulesProvider.getRules("Europe/Paris", false);
        if (rules == null) {
            throw new AssertionError("Expected non-null ZoneRules for Europe/Paris");
        }
    }
}
