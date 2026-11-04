/*
 * @test
 * @summary WITUP generated test for DateFormatSymbolsProviderImpl.getInstance(Locale)
 * @modules java.base/sun.util.locale.provider
 * @run main DateFormatSymbolsProviderImplWitupTest
 */

import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import sun.util.locale.provider.DateFormatSymbolsProviderImpl;

public class DateFormatSymbolsProviderImplWitupTest {
    public static void main(String[] args) {
        Set<String> tags = Collections.singleton("en-US");
        DateFormatSymbolsProviderImpl provider = new DateFormatSymbolsProviderImpl(null, tags);

        boolean npeThrown = false;
        try {
            provider.getInstance(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("expected NullPointerException for null locale");
        }

        DateFormatSymbols symbols = provider.getInstance(Locale.US);
        if (symbols == null) {
            throw new RuntimeException("getInstance(Locale.US) returned null");
        }
        if (!symbols.equals(new DateFormatSymbols(Locale.US))) {
            throw new RuntimeException("getInstance(Locale.US) differs from new DateFormatSymbols(Locale.US)");
        }

        DateFormatSymbols second = provider.getInstance(Locale.US);
        if (symbols == second) {
            throw new RuntimeException("getInstance should return a new instance on each call");
        }
    }
}
