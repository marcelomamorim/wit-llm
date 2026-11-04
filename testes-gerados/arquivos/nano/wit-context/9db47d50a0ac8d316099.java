/* @test    @summary Test getInstance with null locale to verify NullPointerException is thrown    @run main DateFormatSymbolsProviderImplWitupTest */

import java.text.DateFormatSymbols;
import java.util.Locale;

public class DateFormatSymbolsProviderImplWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithNullLocale();
    }

    public static void testGetInstanceWithNullLocale() {
        try {
            sun.util.locale.provider.DateFormatSymbolsProviderImpl provider =
                new sun.util.locale.provider.DateFormatSymbolsProviderImpl(
                    sun.util.locale.provider.LocaleProviderAdapter.Type.DEFAULT, null);
            provider.getInstance(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException expected) {
            // Test passes as exception is expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
