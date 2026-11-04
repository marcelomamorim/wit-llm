/*
 * @test
 * @summary Verifies that Bundles.of rejects null baseName, null locale and null strategy with NullPointerException
 * @modules java.base/sun.util.resources
 * @run main BundlesWitupTest
 */

import java.util.Locale;
import sun.util.resources.Bundles;

public class BundlesWitupTest {

    public static void main(String[] args) {
        expectNpe(() -> Bundles.of(null, Locale.US, null));
        expectNpe(() -> Bundles.of("test.WitupBundle", null, null));
        expectNpe(() -> Bundles.of("test.WitupBundle", Locale.US, null));
    }

    private static void expectNpe(Runnable action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
