/* @test    @summary Test getDisplayCountry method of LocaleNameProviderImpl    @run main LocaleNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        testGetDisplayCountryWithValidInputs();
        testGetDisplayCountryWithNullCountry();
        testGetDisplayCountryWithNullLocale();
        testGetDisplayCountryWithInvalidCountryCode();
    }

    private static void testGetDisplayCountryWithValidInputs() {
        Locale locale = Locale.US;
        String countryCode = "FR";
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        String result = provider.getDisplayCountry(countryCode, locale);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty display country for valid inputs");
        }
    }

    private static void testGetDisplayCountryWithNullCountry() {
        Locale locale = Locale.FRANCE;
        try {
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            String result = provider.getDisplayCountry(null, locale);
            if (result != null) {
                throw new AssertionError("Expected null result when country code is null");
            }
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayCountryWithNullLocale() {
        String countryCode = "DE";
        try {
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            provider.getDisplayCountry(countryCode, null);
            throw new AssertionError("Expected NullPointerException when locale is null");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayCountryWithInvalidCountryCode() {
        Locale locale = Locale.UK;
        String invalidCountryCode = "XYZ";
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        String result = provider.getDisplayCountry(invalidCountryCode, locale);
        if (result == null) {
            throw new AssertionError("Expected non-null result for invalid country code");
        }
    }
}
