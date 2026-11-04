/*
 * @test
 * @summary Witup-generated checks for CurrencyNameProviderImpl.getSymbol
 * @modules java.base/sun.util.locale.provider
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;

public class CurrencyNameProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> tags = new HashSet<>();
        tags.add("en-US");
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(null, tags);

        testNullCurrencyCodeThrowsNPE(provider);
        testLowerCaseCodeIsNormalizedToUpperCase(provider);
        testMixedCaseCodeIsNormalizedToUpperCase(provider);
    }

    static void testNullCurrencyCodeThrowsNPE(CurrencyNameProviderImpl provider) {
        try {
            provider.getSymbol(null, Locale.US);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null currency code");
    }

    static void testLowerCaseCodeIsNormalizedToUpperCase(CurrencyNameProviderImpl provider) {
        String upper = provider.getSymbol("USD", Locale.US);
        String lower = provider.getSymbol("usd", Locale.US);
        if (upper == null ? lower != null : !upper.equals(lower)) {
            throw new RuntimeException("Lower-case code result differs from upper-case: "
                    + lower + " vs " + upper);
        }
    }

    static void testMixedCaseCodeIsNormalizedToUpperCase(CurrencyNameProviderImpl provider) {
        String upper = provider.getSymbol("EUR", Locale.US);
        String mixed = provider.getSymbol("Eur", Locale.US);
        if (upper == null ? mixed != null : !upper.equals(mixed)) {
            throw new RuntimeException("Mixed-case code result differs from upper-case: "
                    + mixed + " vs " + upper);
        }
    }
}
