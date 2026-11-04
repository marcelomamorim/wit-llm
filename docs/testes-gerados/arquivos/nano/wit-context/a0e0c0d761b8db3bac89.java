/* @test    @summary Test getDisplayCountry method for null inputs to verify NullPointerException is thrown    @run main LocaleNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDisplayCountry_NullCtry();
        testGetDisplayCountry_NullLocale();
    }

    private static void testGetDisplayCountry_NullCtry() {
        try {
            Locale locale = Locale.US;
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            provider.getDisplayCountry(null, locale);
            System.err.println("Failed: Expected NullPointerException for null ctry");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null ctry: " + e);
        }
    }

    private static void testGetDisplayCountry_NullLocale() {
        try {
            String ctry = "US";
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            provider.getDisplayCountry(ctry, null);
            System.err.println("Failed: Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null locale: " + e);
        }
    }
}
