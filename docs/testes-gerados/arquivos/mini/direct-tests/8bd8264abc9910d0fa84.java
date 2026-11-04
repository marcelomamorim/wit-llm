/*
 * @test
 * @summary Test ZoneRulesProvider.registerProvider static method
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {

    // Minimal subclass to allow instantiation
    static class DummyProvider extends ZoneRulesProvider {
        protected DummyProvider() {
            super();
        }
        @Override
        public java.util.Set<String> getAvailableZoneIds() {
            return java.util.Collections.emptySet();
        }
        @Override
        public java.time.zone.ZoneRules getRules(String zoneId, boolean forCaching) {
            throw new UnsupportedOperationException();
        }
        @Override
        public java.time.zone.ZoneRules getVersionedRules(String zoneId, String version) {
            throw new UnsupportedOperationException();
        }
        @Override
        public java.util.NavigableMap<String, java.time.zone.ZoneRules> getVersions(String zoneId) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        testRegisterProviderWithNull();
        testRegisterProviderWithValid();
    }

    private static void testRegisterProviderWithNull() {
        try {
            ZoneRulesProvider.registerProvider(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"provider".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testRegisterProviderWithValid() {
        DummyProvider provider = new DummyProvider();
        // Register the provider - no exception expected
        ZoneRulesProvider.registerProvider(provider);
        // No direct way to verify internal state, but no exception means success
    }
}
