/*
 * @test
 * @summary Verify Provider.values() returns an unmodifiable view that reflects provider entries
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collection;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProv", "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Collection<Object> initial = p.values();
        if (initial == null) {
            throw new RuntimeException("values() returned null");
        }

        p.put("Test.Key", "testValue");
        if (!p.values().contains("testValue")) {
            throw new RuntimeException("values() does not reflect put entry");
        }

        try {
            p.values().add("x");
            throw new RuntimeException("values() collection is modifiable");
        } catch (UnsupportedOperationException expected) {
            // expected: the returned collection must be unmodifiable
        }
    }
}
