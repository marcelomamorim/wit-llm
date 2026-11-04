/* @test    @summary Test for sun.util.resources.Bundles.of method with various null inputs    @run main BundlesWitupTest */

import java.util.Locale;
import java.util.ResourceBundle;

public class BundlesWitupTest {

    public static void main(String[] args) {
        testNullBaseName();
        testNullLocale();
        testNullStrategy();
    }

    private static void testNullBaseName() {
        try {
            ResourceBundle rb = sun.util.resources.Bundles.of(null, Locale.US, null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null baseName");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null baseName: " + t);
        }
    }

    private static void testNullLocale() {
        try {
            ResourceBundle rb = sun.util.resources.Bundles.of("someBase", null, null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null locale");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null locale: " + t);
        }
    }

    private static void testNullStrategy() {
        try {
            ResourceBundle rb = sun.util.resources.Bundles.of("someBase", Locale.US, null);
            // Depending on implementation, strategy may be null, so no exception expected here
            if (rb != null) {
                System.out.println("Passed: No exception for null strategy, returned ResourceBundle");
            } else {
                System.err.println("Failed: Null strategy returned null ResourceBundle");
            }
        } catch (NullPointerException e) {
            System.out.println("Failed: NullPointerException thrown for null strategy");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null strategy: " + t);
        }
    }
}
