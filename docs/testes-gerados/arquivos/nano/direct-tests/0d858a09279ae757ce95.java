/* @test    @summary Test getDisplayName method of CurrencyNameProviderImpl for various inputs    @run main CurrencyNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.CurrencyNameProviderImpl;

public class CurrencyNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithValidCurrencyCodeAndLocale();
        testGetDisplayNameWithLowerCaseCurrencyCode();
        testGetDisplayNameWithInvalidCurrencyCode();
        testGetDisplayNameWithNullCurrencyCode();
        testGetDisplayNameWithNullLocale();
    }

    private static void testGetDisplayNameWithValidCurrencyCodeAndLocale() {
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(
            null, null);
        String result = provider.getDisplayName("USD", Locale.US);
        System.out.println("Test valid currency code and locale: " + result);
        // Expect a non-null, non-empty string
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty display name");
        }
    }

    private static void testGetDisplayNameWithLowerCaseCurrencyCode() {
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(
            null, null);
        String result = provider.getDisplayName("eur", Locale.FRANCE);
        System.out.println("Test lowercase currency code: " + result);
        // Expect a non-null, non-empty string
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty display name");
        }
    }

    private static void testGetDisplayNameWithInvalidCurrencyCode() {
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(
            null, null);
        String result = provider.getDisplayName("XYZ", Locale.US);
        System.out.println("Test invalid currency code: " + result);
        // Expect null or empty string if invalid
        if (result != null && !result.isEmpty()) {
            throw new AssertionError("Expected null or empty for invalid currency code");
        }
    }

    private static void testGetDisplayNameWithNullCurrencyCode() {
        try {
            CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(
                null, null);
            provider.getDisplayName(null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null currencyCode");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null currencyCode");
        }
    }

    private static void testGetDisplayNameWithNullLocale() {
        try {
            CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(
                null, null);
            provider.getDisplayName("USD", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null locale");
        }
    }
}
