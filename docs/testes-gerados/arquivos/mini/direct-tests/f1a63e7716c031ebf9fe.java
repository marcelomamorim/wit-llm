/*
 * @test
 * @summary Test getCurrencyInstance(Locale) method of NumberFormatProviderImpl
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.NumberFormat;
import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.NumberFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Prepare a dummy LocaleProviderAdapter.Type for construction
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;
        // Prepare a set of langtags containing "en"
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en");

        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(type, langtags);

        // Test with a supported locale
        Locale locale = Locale.ENGLISH;
        NumberFormat nf = provider.getCurrencyInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getCurrencyInstance returned null for supported locale");
        }

        // Test with a locale that is likely unsupported (empty langtags)
        NumberFormatProviderImpl providerEmpty = new NumberFormatProviderImpl(type, new HashSet<String>());
        Locale unsupportedLocale = new Locale("zz", "ZZ");
        // The behavior for unsupported locale is not specified to throw, so just call and check not null
        NumberFormat nf2 = providerEmpty.getCurrencyInstance(unsupportedLocale);
        if (nf2 == null) {
            throw new RuntimeException("getCurrencyInstance returned null for unsupported locale");
        }

        // Test with null locale - expect NullPointerException
        boolean npeThrown = false;
        try {
            provider.getCurrencyInstance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }

        System.out.println("All tests passed.");
    }
}
