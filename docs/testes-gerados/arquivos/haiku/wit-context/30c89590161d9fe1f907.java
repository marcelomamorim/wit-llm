/*
 * @test
 * @summary WITUP-derived check that getPercentInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {
    public static void main(String[] args) {
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(null, null);
        try {
            provider.getPercentInstance(null);
            throw new RuntimeException("expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }
    }
}
