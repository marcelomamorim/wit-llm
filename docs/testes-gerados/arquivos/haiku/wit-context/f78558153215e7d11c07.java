/*
 * @test
 * @summary Verifies that ZoneRegion.getRules() returns the rules registered for the region id and is consistent with ZoneRulesProvider
 * @run main ZoneRegionWitupTest
 */

import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;

public class ZoneRegionWitupTest {

    public static void main(String[] args) {
        ZoneId zone = ZoneId.of("Europe/Paris");

        ZoneRules rules = zone.getRules();
        if (rules == null) {
            throw new RuntimeException("getRules() returned null for Europe/Paris");
        }

        ZoneRules provided = ZoneRulesProvider.getRules("Europe/Paris", false);
        if (!rules.equals(provided)) {
            throw new RuntimeException("getRules() differs from ZoneRulesProvider rules for Europe/Paris");
        }

        if (!rules.equals(zone.getRules())) {
            throw new RuntimeException("repeated getRules() calls are not consistent");
        }

        ZoneRules tokyo = ZoneId.of("Asia/Tokyo").getRules();
        if (tokyo == null) {
            throw new RuntimeException("getRules() returned null for Asia/Tokyo");
        }
        if (rules.equals(tokyo)) {
            throw new RuntimeException("Europe/Paris and Asia/Tokyo should not share the same rules");
        }
    }
}
