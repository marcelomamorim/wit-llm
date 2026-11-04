/*
 * @test
 * @summary ZoneRulesProvider.getRules null-check and basic UTC rules behavior
 * @run main ZoneRulesProviderWitupTest
 */
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;

public class ZoneRulesProviderWitupTest {
    public static void main(String[] args) {
        testNullZoneIdThrowsNpe();
        testUtcRules(false);
        testUtcRules(true);
    }

    static void testNullZoneIdThrowsNpe() {
        try {
            ZoneRulesProvider.getRules(null, false);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null zoneId");
    }

    static void testUtcRules(boolean forCaching) {
        ZoneRules rules = ZoneRulesProvider.getRules("UTC", forCaching);
        if (rules == null) {
            throw new AssertionError("getRules returned null for UTC");
        }
        if (!rules.isFixedOffset()) {
            throw new AssertionError("UTC rules should be a fixed offset");
        }
        ZoneOffset offset = rules.getOffset(Instant.EPOCH);
        if (!ZoneOffset.UTC.equals(offset)) {
            throw new AssertionError("unexpected offset for UTC: " + offset);
        }
    }
}
