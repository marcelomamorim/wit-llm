/*
 * @test
 * @summary Verifies case-insensitive lookup and null-argument handling of CurrencyNameProviderImpl.getDisplayName
 * @modules java.base/sun.util.locale.provider
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CurrencyNameProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> tags = new HashSet<>();
        tags.add("en-US");
        CurrencyNameProviderImpl provider =
                new CurrencyNameProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        testCaseInsensitiveUpperVsLower(provider);
        testCaseInsensitiveMixedCase(provider);
        testRepeatedCallsAreConsistent(provider);
        testNullCurrencyCodeThrowsNPE(provider);
    }

    static void testCaseInsensitiveUpperVsLower(CurrencyNameProviderImpl provider) {
        String upper = provider.getDisplayName("USD", Locale.US);
        String lower = provider.getDisplayName("usd", Locale.US);
        if (!equalsNullable(upper, lower)) {
            throw new RuntimeException("Expected same result for USD and usd, got "
                    + upper + " and " + lower);
        }
    }

    static void testCaseInsensitiveMixedCase(CurrencyNameProviderImpl provider) {
        String upper = provider.getDisplayName("EUR", Locale.US);
        String mixed = provider.getDisplayName("Eur", Locale.US);
        if (!equalsNullable(upper, mixed)) {
            throw new RuntimeException("Expected same result for EUR and Eur, got "
                    + upper + " and " + mixed);
        }
    }

    static void testRepeatedCallsAreConsistent(CurrencyNameProviderImpl provider) {
        String first = provider.getDisplayName("JPY", Locale.US);
        String second = provider.getDisplayName("JPY", Locale.US);
        if (!equalsNullable(first, second)) {
            throw new RuntimeException("Repeated calls differ: " + first + " vs " + second);
        }
    }

    static void testNullCurrencyCodeThrowsNPE(CurrencyNameProviderImpl provider) {
        boolean thrown = false;
        try {
            provider.getDisplayName(null, Locale.US);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null currencyCode");
        }
    }

    static boolean equalsNullable(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }
}
