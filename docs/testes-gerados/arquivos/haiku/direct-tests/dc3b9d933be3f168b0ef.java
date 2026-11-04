/*
 * @test
 * @summary Verifies that NumberFormatProviderImpl.getNumberInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import java.util.Collections;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        NumberFormatProviderImpl provider =
                new NumberFormatProviderImpl(null, Collections.<String>emptySet());
        try {
            provider.getNumberInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected behavior
        }
    }
}
