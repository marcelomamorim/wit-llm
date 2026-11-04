/*
 * @test
 * @summary Witup-derived checks for NumberFormatProviderImpl.getNumberInstance
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.values()[0];
        NumberFormatProviderImpl provider =
                new NumberFormatProviderImpl(type, Collections.<String>emptySet());

        boolean npeThrown = false;
        try {
            provider.getNumberInstance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("getNumberInstance(null) must throw NullPointerException");
        }

        NumberFormat nf = provider.getNumberInstance(Locale.US);
        if (nf == null) {
            throw new AssertionError("getNumberInstance(Locale.US) returned null");
        }
    }
}
