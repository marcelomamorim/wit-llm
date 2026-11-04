/*
 * @test
 * @summary Verifies that CollatorProviderImpl.getInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main CollatorProviderImplWitupTest
 */

import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.CollatorProviderImpl;

public class CollatorProviderImplWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNPE();
    }

    static void testNullLocaleThrowsNPE() {
        CollatorProviderImpl provider = new CollatorProviderImpl(null, Collections.<String>emptySet());
        boolean thrown = false;
        try {
            provider.getInstance((Locale) null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }
}
