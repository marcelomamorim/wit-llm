/*
 * @test
 * @summary Verifies observable return values of Provider.put for repeated string-valued keys
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    private static final class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Object first = p.put("witupKey", "v1");
        if (first != null) {
            throw new RuntimeException("Expected null for first put, got: " + first);
        }

        if (!"v1".equals(p.getProperty("witupKey"))) {
            throw new RuntimeException("Expected v1 after first put");
        }

        Object second = p.put("witupKey", "v2");
        if (!"v1".equals(second)) {
            throw new RuntimeException("Expected previous value v1, got: " + second);
        }

        if (!"v2".equals(p.getProperty("witupKey"))) {
            throw new RuntimeException("Expected v2 after second put");
        }
    }
}
