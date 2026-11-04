/* @test    @summary Test getInstance with null locale to verify NullPointerException is thrown    @run main DecimalFormatSymbolsProviderImplWitupTest */

import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormatSymbolsProviderImplWitupTest {

    public static void main(String[] args) {
        testGetInstanceNullLocale();
    }

    public static void testGetInstanceNullLocale() {
        try {
            DecimalFormatSymbolsProviderImpl provider = new DecimalFormatSymbolsProviderImpl(null, null);
            provider.getInstance(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
