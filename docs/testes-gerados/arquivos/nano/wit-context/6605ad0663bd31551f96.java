/*
 * @test
 * @summary Test getRules with null zoneId to verify NullPointerException
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testGetRulesNullZoneId();
    }

    public static void testGetRulesNullZoneId() {
        try {
            ZoneRulesProvider.getRules(null, false);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
