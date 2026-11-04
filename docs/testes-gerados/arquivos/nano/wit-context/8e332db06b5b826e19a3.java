/* @test    @summary Test for getDisplayUnicodeExtensionType method in LocaleNameProviderImpl    @run main LocaleNameProviderImplWitupTest */

import sun.util.locale.provider.LocaleNameProviderImpl;
import java.util.Locale;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        testNullExtType();
        testNullKey();
        testNullLocale();
    }

    private static void testNullExtType() {
        LocaleNameProviderImpl obj = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        try {
            String result = obj.getDisplayUnicodeExtensionType(null, "someKey", Locale.US);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null extType");
        } catch (NullPointerException e) {
            // Test passes
        }
    }

    private static void testNullKey() {
        LocaleNameProviderImpl obj = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        try {
            String result = obj.getDisplayUnicodeExtensionType("someExt", null, Locale.US);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // Test passes
        }
    }

    private static void testNullLocale() {
        LocaleNameProviderImpl obj = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        try {
            String result = obj.getDisplayUnicodeExtensionType("someExt", "someKey", null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Test passes
        }
    }
}
