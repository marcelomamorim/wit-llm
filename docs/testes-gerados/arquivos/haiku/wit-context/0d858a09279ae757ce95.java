/*
 * @test
 * @summary WITUP-derived checks for CurrencyNameProviderImpl.getDisplayName null handling
 * @modules java.base/sun.util.locale.provider
 * @run main CurrencyNameProviderImplWitupTest
 */
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.CurrencyNameProviderImpl;

public class CurrencyNameProviderImplWitupTest {
    public static void main(String[] args) {
        CurrencyNameProviderImpl provider =
            new CurrencyNameProviderImpl(null, Collections.<String>emptySet());

        expectNullPointerException(() -> provider.getDisplayName("USD", null));
        expectNullPointerException(() -> provider.getDisplayName(null, Locale.US));
        expectNullPointerException(() -> provider.getDisplayName(null, null));
    }

    private static void expectNullPointerException(Runnable action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException was not thrown");
    }
}
