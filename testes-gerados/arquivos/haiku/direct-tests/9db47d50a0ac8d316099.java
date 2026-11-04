/*
 * @test
 * @summary Verify DateFormatSymbolsProviderImpl.getInstance returns new DateFormatSymbols for non-null locales and throws NullPointerException for null
 * @modules java.base/sun.util.locale.provider
 * @run main DateFormatSymbolsProviderImplWitupTest
 */

import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.DateFormatSymbolsProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class DateFormatSymbolsProviderImplWitupTest {
    public static void main(String[] args) {
        Set<String> tags = Collections.singleton("en-US");
        DateFormatSymbolsProviderImpl provider =
            new DateFormatSymbolsProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        boolean npeThrown = false;
        try {
            provider.getInstance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("NullPointerException expected for null locale");
        }

        DateFormatSymbols first = provider.getInstance(Locale.US);
        if (first == null) {
            throw new RuntimeException("getInstance(Locale.US) returned null");
        }
        if (!first.equals(new DateFormatSymbols(Locale.US))) {
            throw new RuntimeException("getInstance(Locale.US) differs from new DateFormatSymbols(Locale.US)");
        }

        DateFormatSymbols second = provider.getInstance(Locale.US);
        if (first == second) {
            throw new RuntimeException("getInstance should return a new instance on each call");
        }
        if (!first.equals(second)) {
            throw new RuntimeException("Repeated getInstance calls should yield equal symbols");
        }
    }
}
