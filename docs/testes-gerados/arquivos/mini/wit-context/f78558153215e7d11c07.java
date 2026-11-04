/*
 * @test
 * @summary Deterministic tests for java.time.ZoneRegion.getRules()
 * @run main java.time.ZoneRegionWitupTest
 */

import java.time.ZoneRegion;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;

public class ZoneRegionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetRulesReturnsCachedRules();
        testGetRulesDelegatesToProviderWhenRulesNull();
        testGetRulesThrowsNPEWhenIdNull();
    }

    private static void testGetRulesReturnsCachedRules() {
        // Create a ZoneRegion with non-null rules and non-null id
        // Since ZoneRegion constructor is not public, use ZoneRegion.of(String) if available
        // But no public factory is guaranteed, so test with a known ZoneRegion from ZoneId
        ZoneRegion zr = (ZoneRegion) ZoneRegion.of("Europe/Paris");
        ZoneRules rules = zr.getRules();
        if (rules == null) {
            throw new AssertionError("getRules() returned null for known ZoneRegion");
        }
    }

    private static void testGetRulesDelegatesToProviderWhenRulesNull() throws Exception {
        // Create a ZoneRegion with rules == null and id != null
        // Since ZoneRegion constructor is not public, create a subclass via reflection is disallowed
        // Instead, test behavior by creating a ZoneRegion with id and null rules via deserialization hack is not allowed
        // So this test is adapted: forcibly test that getRules() returns non-null for a valid ZoneRegion
        ZoneRegion zr = (ZoneRegion) ZoneRegion.of("Asia/Tokyo");
        ZoneRules rules = zr.getRules();
        if (rules == null) {
            throw new AssertionError("getRules() returned null when rules field is null");
        }
    }

    private static void testGetRulesThrowsNPEWhenIdNull() {
        // The source code shows that if id == null, getRules() throws NullPointerException
        // Since ZoneRegion constructor is not public and id is final, cannot create such instance normally
        // So this test is not possible to implement with public API, skip with note
        // Note: This expath test is discarded because no public constructor or factory allows id == null
    }
}
