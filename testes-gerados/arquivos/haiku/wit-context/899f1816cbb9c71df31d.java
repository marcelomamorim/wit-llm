/*
 * @test
 * @summary WITUP-derived exception-path checks for CurrencyNameProviderImpl.getSymbol
 * @modules java.base/sun.util.locale.provider
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;

public class CurrencyNameProviderImplWitupTest {

    interface Call {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        Set<String> tags = Collections.singleton("en-US");
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(null, tags);

        expectNullPointerException(() -> provider.getSymbol(null, Locale.US));
        expectNullPointerException(() -> provider.getSymbol("USD", null));
    }

    private static void expectNullPointerException(Call call) throws Exception {
        try {
            call.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
