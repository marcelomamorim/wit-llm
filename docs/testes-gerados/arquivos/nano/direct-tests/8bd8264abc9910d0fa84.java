/*
 * @test
 * @summary Test registration of ZoneRulesProvider instances.
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testRegisterProviderNull();
        testRegisterProviderValid();
    }

    static void testRegisterProviderNull() {
        try {
            ZoneRulesProvider.registerProvider(null);
            throw new AssertionError("Expected NullPointerException for null provider");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRegisterProviderValid() {
        DummyProvider provider = new DummyProvider();
        ZoneRulesProvider.registerProvider(provider);
        if (!ZoneRulesProvider.PROVIDERS.contains(provider)) {
            throw new AssertionError("Provider not registered");
        }
    }

    static class DummyProvider extends ZoneRulesProvider {
        @Override
        protected void provideZoneIds(Set<String> zoneIds) {
            zoneIds.add("DummyZone");
        }
        @Override
        protected void provideRules(String zoneId, boolean forCaching, java.util.List<String> zoneIds) {
            // no-op
        }
        @Override
        protected void provideVersions(String zoneId, java.util.List<String> zoneIds) {
            // no-op
        }
    }
}
