/*
 * @test
 * @summary Verifies DecimalFormatSymbolsProviderImpl.getInstance(Locale) null handling and returned instances
 * @modules java.base/sun.util.locale.provider
 * @run main DecimalFormatSymbolsProviderImplWitupTest
 */

import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class DecimalFormatSymbolsProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> langtags = new HashSet<>();
        langtags.add("en-US");
        DecimalFormatSymbolsProviderImpl provider =
                new DecimalFormatSymbolsProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        testNullLocaleThrowsNPE(provider);
        testGetInstanceReturnsSymbolsForLocale(provider);
        testGetInstanceReturnsNewInstanceEachCall(provider);
    }

    static void testNullLocaleThrowsNPE(DecimalFormatSymbolsProviderImpl provider) {
        boolean thrown = false;
        try {
            provider.getInstance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }

    static void testGetInstanceReturnsSymbolsForLocale(DecimalFormatSymbolsProviderImpl provider) {
        DecimalFormatSymbols actual = provider.getInstance(Locale.US);
        if (actual == null) {
            throw new RuntimeException("getInstance returned null");
        }
        DecimalFormatSymbols expected = new DecimalFormatSymbols(Locale.US);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Symbols for Locale.US differ from DecimalFormatSymbols(Locale.US)");
        }
        if (actual.getDecimalSeparator() != '.') {
            throw new RuntimeException("Unexpected decimal separator for Locale.US: "
                    + actual.getDecimalSeparator());
        }
    }

    static void testGetInstanceReturnsNewInstanceEachCall(DecimalFormatSymbolsProviderImpl provider) {
        DecimalFormatSymbols first = provider.getInstance(Locale.US);
        DecimalFormatSymbols second = provider.getInstance(Locale.US);
        if (first == second) {
            throw new RuntimeException("getInstance should return a new DecimalFormatSymbols instance");
        }
        if (!first.equals(second)) {
            throw new RuntimeException("Repeated getInstance calls should yield equal symbols");
        }
    }
}
