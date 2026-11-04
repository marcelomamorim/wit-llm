/*
 * @test
 * @summary Test sun.util.resources.Bundles.of method for null argument checks and normal behavior
 * @run main BundlesWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.resources.Bundles;

public class BundlesWitupTest {
    public static void main(String[] args) {
        testNullBaseName();
        testNullLocale();
        testNullStrategy();
        testValidCall();
    }

    private static void testNullBaseName() {
        try {
            Bundles.of(null, Locale.US, new DummyStrategy());
            throw new RuntimeException("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullLocale() {
        try {
            Bundles.of("baseName", null, new DummyStrategy());
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullStrategy() {
        try {
            Bundles.of("baseName", Locale.US, null);
            throw new RuntimeException("Expected NullPointerException for null strategy");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidCall() {
        ResourceBundle rb = Bundles.of("baseName", Locale.US, new DummyStrategy());
        if (rb == null) {
            throw new RuntimeException("Bundles.of returned null for valid inputs");
        }
    }

    // Dummy Strategy implementation to satisfy method signature
    private static class DummyStrategy implements Bundles.Strategy {
        // no methods to implement as Strategy is not detailed in context
    }
}
