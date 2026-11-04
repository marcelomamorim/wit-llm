/*
 * @test
 * @summary Test sun.util.locale.provider.DateFormatSymbolsProviderImpl.getInstance(Locale) for null and valid locale
 * @run main DateFormatSymbolsProviderImplWitupTest
 */

import sun.util.locale.provider.DateFormatSymbolsProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DateFormatSymbols;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DateFormatSymbolsProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Setup with dummy type and empty langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        Set<String> langtags = new HashSet<>();
        DateFormatSymbolsProviderImpl provider = new DateFormatSymbolsProviderImpl(dummyType, langtags);

        // Test 1: getInstance(null) should throw NullPointerException
        try {
            provider.getInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: getInstance with a valid locale returns non-null DateFormatSymbols
        Locale testLocale = Locale.US;
        DateFormatSymbols dfs = provider.getInstance(testLocale);
        if (dfs == null) {
            throw new RuntimeException("getInstance returned null for valid locale");
        }

        // Test 3: returned DateFormatSymbols locale matches requested locale
        // Note: DateFormatSymbols#getLocale() is available since JDK 1.7
        Locale dfsLocale = dfs.getLocale(Locale.Category.FORMAT);
        if (!testLocale.equals(dfsLocale)) {
            throw new RuntimeException("DateFormatSymbols locale does not match requested locale");
        }
    }
}
