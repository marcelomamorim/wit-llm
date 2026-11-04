/*
 * @test
 * @summary Verifies that Bundles.of returns null or throws MissingResourceException for a nonexistent bundle under every Strategy
 * @modules java.base/sun.util.resources
 * @run main BundlesWitupTest
 */

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import sun.util.resources.Bundles;

public class BundlesWitupTest {

    private static final String MISSING_BASE_NAME = "sun.util.resources.WitupNonexistentBundle";

    public static void main(String[] args) {
        Bundles.Strategy[] strategies = Bundles.Strategy.values();
        if (strategies.length == 0) {
            throw new RuntimeException("No Bundles.Strategy constants available");
        }
        for (Bundles.Strategy strategy : strategies) {
            try {
                ResourceBundle bundle = Bundles.of(MISSING_BASE_NAME, Locale.ROOT, strategy);
                if (bundle != null) {
                    throw new RuntimeException("Unexpected bundle returned for nonexistent name with strategy " + strategy);
                }
            } catch (MissingResourceException expected) {
                // acceptable public behavior for a missing bundle
            }
        }
    }
}
