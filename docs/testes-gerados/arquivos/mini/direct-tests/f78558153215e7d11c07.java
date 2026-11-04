/*
 * @test
 * @summary Test ZoneRegion.getRules() returns cached rules or queries provider
 * @run main ZoneRegionWitupTest
 */

import java.time.ZoneRegion;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.time.zone.ZoneRulesException;

public class ZoneRegionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetRulesReturnsCachedRules();
        testGetRulesQueriesProviderWhenCachedRulesNull();
        testGetRulesThrowsWhenProviderFails();
    }

    // Test that getRules() returns the cached rules if not null
    private static void testGetRulesReturnsCachedRules() throws Exception {
        // Create a ZoneRegion with a known id and cached rules
        String id = "UTC";
        ZoneRules cachedRules = ZoneRulesProvider.getRules(id, false);
        ZoneRegion region = new ZoneRegion(id, cachedRules);

        ZoneRules rules = region.getRules();
        if (rules == null) {
            throw new AssertionError("getRules() returned null when cached rules present");
        }
        if (rules != cachedRules) {
            throw new AssertionError("getRules() did not return the cached rules instance");
        }
    }

    // Test that getRules() queries ZoneRulesProvider when cached rules is null
    private static void testGetRulesQueriesProviderWhenCachedRulesNull() throws Exception {
        String id = "UTC";
        ZoneRegion region = new ZoneRegion(id, null);

        ZoneRules rules = region.getRules();
        if (rules == null) {
            throw new AssertionError("getRules() returned null when cached rules null and provider queried");
        }
    }

    // Test that getRules() throws ZoneRulesException if provider fails
    private static void testGetRulesThrowsWhenProviderFails() throws Exception {
        // Use an invalid zone id to cause ZoneRulesProvider.getRules to throw
        String invalidId = "Invalid/ZoneId_For_Test";
        ZoneRegion region = new ZoneRegion(invalidId, null);

        try {
            region.getRules();
            throw new AssertionError("Expected ZoneRulesException not thrown for invalid zone id");
        } catch (ZoneRulesException e) {
            // Expected exception
        }
    }

}