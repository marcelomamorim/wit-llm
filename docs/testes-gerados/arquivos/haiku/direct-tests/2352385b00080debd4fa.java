/*
 * @test
 * @summary Verify that Provider.replace returns the previous value and updates the stored property
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "test info");
        }
    }

    public static void main(String[] args) throws Exception {
        TestProvider p = new TestProvider();
        p.put("test.key", "v1");

        Object old = p.replace("test.key", "v2");
        if (!"v1".equals(old)) {
            throw new RuntimeException("Expected previous value v1 but got " + old);
        }

        String current = p.getProperty("test.key");
        if (!"v2".equals(current)) {
            throw new RuntimeException("Expected updated value v2 but got " + current);
        }

        Object old2 = p.replace("test.key", "v3");
        if (!"v2".equals(old2)) {
            throw new RuntimeException("Expected previous value v2 but got " + old2);
        }
        if (!"v3".equals(p.getProperty("test.key"))) {
            throw new RuntimeException("Expected updated value v3");
        }
    }
}
