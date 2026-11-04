/*
 * @test
 * @summary Test sun.util.resources.Bundles.of method basic behavior
 * @run main BundlesWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;
sun.util.resources.Bundles.Strategy;

public class BundlesWitupTest {
    public static void main(String[] args) {
        // Test 1: Call of with valid baseName, locale and a dummy Strategy
        Strategy dummyStrategy = new Strategy() {
            @Override
            public ResourceBundle loadBundle(String baseName, Locale locale) {
                return null; // simulate no bundle found
            }
        };

        ResourceBundle rb = Bundles.of("java.base", Locale.ROOT, dummyStrategy);
        if (rb != null) {
            throw new RuntimeException("Expected null ResourceBundle when dummy strategy returns null");
        }

        // Test 2: Call of with null baseName - expect NullPointerException or handled gracefully
        try {
            Bundles.of(null, Locale.ROOT, dummyStrategy);
            // If no exception, test passes if no crash
        } catch (NullPointerException npe) {
            // Expected NPE, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null baseName", e);
        }

        // Test 3: Call of with null locale - expect NullPointerException or handled gracefully
        try {
            Bundles.of("java.base", null, dummyStrategy);
            // If no exception, test passes if no crash
        } catch (NullPointerException npe) {
            // Expected NPE, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null locale", e);
        }

        // Test 4: Call of with null strategy - expect NullPointerException or handled gracefully
        try {
            Bundles.of("java.base", Locale.ROOT, null);
            throw new RuntimeException("Expected NullPointerException for null strategy");
        } catch (NullPointerException npe) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null strategy", e);
        }

        System.out.println("PASS");
    }
}
