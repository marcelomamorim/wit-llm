/*
 * @test
 * @summary Test sun.util.locale.provider.DecimalFormatSymbolsProviderImpl.getInstance(Locale) method
 * @run main DecimalFormatSymbolsProviderImplWitupTest
 */

import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;

public class DecimalFormatSymbolsProviderImplWitupTest {
    public static void main(String[] args) {
        // Prepare a dummy type and langtags for constructor
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        Set<String> dummyLangtags = new HashSet<>();
        dummyLangtags.add("en");

        DecimalFormatSymbolsProviderImpl provider = new DecimalFormatSymbolsProviderImpl(dummyType, dummyLangtags);

        // Test 1: getInstance with valid locale
        Locale locale = Locale.US;
        DecimalFormatSymbols dfs = provider.getInstance(locale);
        if (dfs == null) {
            throw new RuntimeException("getInstance returned null for valid locale");
        }
        if (!locale.equals(dfs.getLocale())) {
            throw new RuntimeException("DecimalFormatSymbols locale mismatch");
        }

        // Test 2: getInstance with Locale.ROOT (valid locale)
        Locale rootLocale = Locale.ROOT;
        DecimalFormatSymbols dfsRoot = provider.getInstance(rootLocale);
        if (dfsRoot == null) {
            throw new RuntimeException("getInstance returned null for Locale.ROOT");
        }
        if (!rootLocale.equals(dfsRoot.getLocale())) {
            throw new RuntimeException("DecimalFormatSymbols locale mismatch for Locale.ROOT");
        }

        // Test 3: getInstance with null locale should throw NullPointerException
        try {
            provider.getInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
