/*
 * @test
 * @summary Test the Bundles.of method for various inputs.
 * @run main BundlesWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class BundlesWitupTest {

    public static void main(String[] args) {
        testNullBaseName();
        testNullLocale();
        testDefaultStrategy();
        testKnownBaseNameAndLocale();
    }

    static void testNullBaseName() {
        try {
            ResourceBundle rb = sun.util.resources.Bundles.of(null, Locale.ENGLISH, null);
            System.out.println("testNullBaseName: " + rb);
        } catch (NullPointerException e) {
            System.out.println("testNullBaseName: Caught expected NullPointerException");
        }
    }

    static void testNullLocale() {
        try {
            ResourceBundle rb = sun.util.resources.Bundles.of("java.util", null, null);
            System.out.println("testNullLocale: " + rb);
        } catch (NullPointerException e) {
            System.out.println("testNullLocale: Caught expected NullPointerException");
        }
    }

    static void testDefaultStrategy() {
        ResourceBundle rb = sun.util.resources.Bundles.of("java.util", Locale.US, null);
        if (rb != null) {
            System.out.println("testDefaultStrategy: ResourceBundle class: " + rb.getClass().getName());
        } else {
            System.out.println("testDefaultStrategy: ResourceBundle is null");
        }
    }

    static void testKnownBaseNameAndLocale() {
        ResourceBundle rb = sun.util.resources.Bundles.of("sun.util.resources.LocaleNames", Locale.ENGLISH, null);
        if (rb != null) {
            System.out.println("testKnownBaseNameAndLocale: ResourceBundle class: " + rb.getClass().getName());
        } else {
            System.out.println("testKnownBaseNameAndLocale: ResourceBundle is null");
        }
    }
}
