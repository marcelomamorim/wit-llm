/*
 * @test
 * @summary Checks observable putIfAbsent behavior of java.security.Provider for absent and present keys
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Object first = p.putIfAbsent("WitupKey", "v1");
        if (first != null) {
            throw new RuntimeException("Expected null for absent key, got: " + first);
        }

        Object second = p.putIfAbsent("WitupKey", "v2");
        if (!"v1".equals(second)) {
            throw new RuntimeException("Expected existing value v1, got: " + second);
        }

        Object other = p.putIfAbsent("WitupOther", "v3");
        if (other != null) {
            throw new RuntimeException("Expected null for another absent key, got: " + other);
        }
    }
}
