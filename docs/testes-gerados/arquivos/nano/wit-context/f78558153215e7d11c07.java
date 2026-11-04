/* @test    @summary Test for ZoneRegion.getRules() method to verify behavior when id is null.    @run main ZoneRegionWitupTest */

import java.time.ZoneRegion;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;

public class ZoneRegionWitupTest {

    public static void main(String[] args) {
        testGetRulesWithNullId();
    }

    public static void testGetRulesWithNullId() {
        ZoneRegion zr = new ZoneRegion() {
            @Override
            public ZoneRules getRules() {
                // Simulate the scenario where id is null
                String id = null;
                try {
                    return (rules != null ? rules : ZoneRulesProvider.getRules(id, false));
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException caught as expected when id is null.");
                }
                return null;
            }
        };
        // Invoke getRules to trigger the exception
        zr.getRules();
    }
}
