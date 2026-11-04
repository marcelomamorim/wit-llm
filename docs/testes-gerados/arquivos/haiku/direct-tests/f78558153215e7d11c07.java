/*
 * @test
 * @summary Verifies that ZoneRegion.getRules() returns the rules of the region and is stable across calls
 * @run main ZoneRegionWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;

public class ZoneRegionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ZoneId paris = ZoneId.of("Europe/Paris");

        ZoneRules rules = paris.getRules();
        check(rules != null, "getRules() returned null");

        ZoneRules again = paris.getRules();
        check(rules.equals(again), "getRules() not stable across calls");

        ZoneRules fromOther = ZoneId.of("Europe/Paris").getRules();
        check(rules.equals(fromOther), "rules differ for same region id");

        Instant winter = Instant.parse("2018-01-15T00:00:00Z");
        check(rules.getOffset(winter).equals(ZoneOffset.of("+01:00")),
              "unexpected winter offset for Europe/Paris");

        Instant summer = Instant.parse("2018-07-15T00:00:00Z");
        check(rules.getOffset(summer).equals(ZoneOffset.of("+02:00")),
              "unexpected summer offset for Europe/Paris");

        ZoneRules tokyo = ZoneId.of("Asia/Tokyo").getRules();
        check(tokyo.getOffset(winter).equals(ZoneOffset.of("+09:00")),
              "unexpected offset for Asia/Tokyo");
        check(!rules.equals(tokyo), "different regions returned equal rules");
    }
}
