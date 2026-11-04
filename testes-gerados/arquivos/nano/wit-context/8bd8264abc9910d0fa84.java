/*
 * @test
 * @summary Test for registerProvider method in ZoneRulesProvider.
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.util.Objects;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testRegisterProviderWithNull();
        testRegisterProviderWithValidObject();
    }

    private static void testRegisterProviderWithNull() {
        boolean exceptionThrown = false;
        try {
            ZoneRulesProvider.registerProvider(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when registering null provider");
        }
    }

    private static void testRegisterProviderWithValidObject() {
        // Since ZoneRulesProvider is abstract and constructor protected,
        // we create a minimal anonymous subclass for testing.
        ZoneRulesProvider provider = new ZoneRulesProvider() {
            // No additional implementation needed for this test.
        };
        try {
            ZoneRulesProvider.registerProvider(provider);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when registering valid provider", e);
        }
    }
}
