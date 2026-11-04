/*
 * @test
 * @summary Test sun.util.locale.provider.DateFormatSymbolsProviderImpl.getInstance(Locale) method
 * @run main DateFormatSymbolsProviderImplWitupTest
 */

import sun.util.locale.provider.DateFormatSymbolsProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;

public class DateFormatSymbolsProviderImplWitupTest {
    public static void main(String[] args) {
        // Prepare a dummy langtags set
        Set<String> langtags = new HashSet<String>();
        langtags.add("en");
        langtags.add("fr");

        // Use LocaleProviderAdapter.Type.JRE as a valid type (exists in JDK 11)
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;

        DateFormatSymbolsProviderImpl provider = new DateFormatSymbolsProviderImpl(type, langtags);

        // Test 1: getInstance with valid locale "en"
        Locale enLocale = Locale.ENGLISH;
        DateFormatSymbols dfsEn = provider.getInstance(enLocale);
        if (dfsEn == null) {
            throw new RuntimeException("getInstance returned null for Locale.ENGLISH");
        }
        if (!enLocale.equals(dfsEn.getLocale())) {
            throw new RuntimeException("DateFormatSymbols locale mismatch for Locale.ENGLISH");
        }

        // Test 2: getInstance with valid locale "fr"
        Locale frLocale = Locale.FRENCH;
        DateFormatSymbols dfsFr = provider.getInstance(frLocale);
        if (dfsFr == null) {
            throw new RuntimeException("getInstance returned null for Locale.FRENCH");
        }
        if (!frLocale.equals(dfsFr.getLocale())) {
            throw new RuntimeException("DateFormatSymbols locale mismatch for Locale.FRENCH");
        }

        // Test 3: getInstance with Locale.ROOT
        Locale rootLocale = Locale.ROOT;
        DateFormatSymbols dfsRoot = provider.getInstance(rootLocale);
        if (dfsRoot == null) {
            throw new RuntimeException("getInstance returned null for Locale.ROOT");
        }
        if (!rootLocale.equals(dfsRoot.getLocale())) {
            throw new RuntimeException("DateFormatSymbols locale mismatch for Locale.ROOT");
        }

        // Test 4: getInstance with null locale should throw NullPointerException
        try {
            provider.getInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
