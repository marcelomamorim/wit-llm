/* @test    @summary Test getInstance with null locale to verify NullPointerException    @run main CollatorProviderImplWitupTest */

import sun.util.locale.provider.CollatorProviderImpl;
import java.util.Locale;

public class CollatorProviderImplWitupTest {
    public static void main(String[] args) {
        testGetInstanceNullLocale();
    }

    public static void testGetInstanceNullLocale() {
        try {
            CollatorProviderImpl instance = new CollatorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            instance.getInstance(null);
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
