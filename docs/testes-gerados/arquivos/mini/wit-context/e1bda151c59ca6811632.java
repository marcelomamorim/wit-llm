/*
 * @test
 * @summary Test sun.util.locale.provider.DecimalFormatSymbolsProviderImpl.getInstance(Locale) for null and valid locale
 * @run main DecimalFormatSymbolsProviderImplWitupTest
 */

import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DecimalFormatSymbolsProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup: create instance with dummy type and empty langtags
        Set<String> langtags = new HashSet<String>();
        DecimalFormatSymbolsProviderImpl provider = new DecimalFormatSymbolsProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test 1: getInstance with null locale should throw NullPointerException
        try {
            provider.getInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: getInstance with a valid locale returns non-null DecimalFormatSymbols
        Locale testLocale = Locale.US;
        DecimalFormatSymbols dfs = provider.getInstance(testLocale);
        if (dfs == null) {
            throw new RuntimeException("getInstance returned null for valid locale");
        }
        if (!testLocale.equals(dfs.getLocale())) {
            throw new RuntimeException("Returned DecimalFormatSymbols locale does not match input locale");
        }
    }
}
