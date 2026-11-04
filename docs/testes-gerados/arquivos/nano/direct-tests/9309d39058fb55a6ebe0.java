/*
 * @test
 * @summary Test the values() method of java.security.Provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collection;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testValuesReturnsUnmodifiableCollection();
    }

    public static void testValuesReturnsUnmodifiableCollection() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        Collection<Object> values = provider.values();
        if (values == null) {
            throw new AssertionError("values() returned null");
        }
        try {
            values.clear();
            throw new AssertionError("Collection returned by values() should be unmodifiable, but clear() did not throw");
        } catch (UnsupportedOperationException expected) {
            // expected exception
        }
    }
}
