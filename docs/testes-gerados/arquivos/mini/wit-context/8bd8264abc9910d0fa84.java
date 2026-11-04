/*
 * @test
 * @summary Test ZoneRulesProvider.registerProvider for null argument and normal registration
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testRegisterProviderNull();
        testRegisterProviderNormal();
    }

    private static void testRegisterProviderNull() {
        try {
            ZoneRulesProvider.registerProvider(null);
            throw new AssertionError("Expected NullPointerException for null provider");
        } catch (NullPointerException npe) {
            if (!"provider".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testRegisterProviderNormal() {
        // Create a minimal subclass instance to test registration
        ZoneRulesProvider provider = new ZoneRulesProvider() {
            @Override
            public String getVersion(String zoneId) {
                return "v1";
            }

            @Override
            public java.time.zone.ZoneRules getRules(String zoneId, boolean forCaching) {
                return null;
            }

            @Override
            public java.util.Set<String> getAvailableZoneIds() {
                return java.util.Collections.emptySet();
            }
        };

        // Register the provider - no exception expected
        ZoneRulesProvider.registerProvider(provider);
    }
}
