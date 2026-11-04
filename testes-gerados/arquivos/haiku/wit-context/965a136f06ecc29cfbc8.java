/*
 * @test
 * @summary WITUP-generated check that getCharacterInstance rejects a null locale and that the provider reports its configured locales
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(
                LocaleProviderAdapter.Type.JRE, Collections.singleton("en"));

        Locale[] locales = provider.getAvailableLocales();
        if (locales == null || locales.length != 1) {
            throw new RuntimeException("expected exactly one available locale");
        }
        if (!"en".equals(locales[0].toString())) {
            throw new RuntimeException("unexpected locale: " + locales[0]);
        }

        boolean thrown = false;
        try {
            provider.getCharacterInstance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null locale");
        }
    }
}
