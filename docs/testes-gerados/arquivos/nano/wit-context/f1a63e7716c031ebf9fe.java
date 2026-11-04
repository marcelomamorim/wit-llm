/* @test    @summary Test getCurrencyInstance with null locale to verify NullPointerException is thrown    @run main NumberFormatProviderImplWitupTest */

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetCurrencyInstanceNullLocale();
    }

    public static void testGetCurrencyInstanceNullLocale() {
        try {
            NumberFormat nf = new sun.util.locale.provider.NumberFormatProviderImpl(null, null).getCurrencyInstance(null);
            // If no exception, test fails
            System.err.println("Failed: Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
