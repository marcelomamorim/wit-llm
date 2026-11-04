/*
 * @test
 * @summary Verifies DecimalFormatSymbolsProviderImpl.getInstance(Locale) null handling and observable DecimalFormatSymbols results
 * @modules java.base/sun.util.locale.provider
 * @run main DecimalFormatSymbolsProviderImplWitupTest
 */

import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;

public class DecimalFormatSymbolsProviderImplWitupTest {

    public static void main(String[] args) {
        DecimalFormatSymbolsProviderImpl provider =
                new DecimalFormatSymbolsProviderImpl(null, Collections.<String>emptySet());
        testNullLocaleThrowsNPE(provider);
        testUsLocaleSymbols(provider);
        testGermanLocaleSymbols(provider);
        testReturnsNewInstancePerCall(provider);
    }

    private static void testNullLocaleThrowsNPE(DecimalFormatSymbolsProviderImpl provider) {
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

    private static void testUsLocaleSymbols(DecimalFormatSymbolsProviderImpl provider) {
        DecimalFormatSymbols symbols = provider.getInstance(Locale.US);
        if (symbols == null) {
            throw new RuntimeException("getInstance(Locale.US) returned null");
        }
        if (symbols.getDecimalSeparator() != '.') {
            throw new RuntimeException("Unexpected decimal separator for US: "
                    + symbols.getDecimalSeparator());
        }
        if (symbols.getGroupingSeparator() != ',') {
            throw new RuntimeException("Unexpected grouping separator for US: "
                    + symbols.getGroupingSeparator());
        }
    }

    private static void testGermanLocaleSymbols(DecimalFormatSymbolsProviderImpl provider) {
        DecimalFormatSymbols symbols = provider.getInstance(Locale.GERMANY);
        if (symbols == null) {
            throw new RuntimeException("getInstance(Locale.GERMANY) returned null");
        }
        if (symbols.getDecimalSeparator() != ',') {
            throw new RuntimeException("Unexpected decimal separator for GERMANY: "
                    + symbols.getDecimalSeparator());
        }
    }

    private static void testReturnsNewInstancePerCall(DecimalFormatSymbolsProviderImpl provider) {
        DecimalFormatSymbols first = provider.getInstance(Locale.US);
        DecimalFormatSymbols second = provider.getInstance(Locale.US);
        if (first == second) {
            throw new RuntimeException("getInstance returned the same instance on consecutive calls");
        }
    }
}
