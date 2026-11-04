/*
 * @test
 * @summary Verifies that Provider.clear() removes all provider properties and leaves the provider usable
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProv", "1.0", "info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        p.put("Alg.Foo", "Bar");
        if (p.size() != 1) {
            throw new RuntimeException("Expected size 1 after put, got " + p.size());
        }

        p.clear();
        if (p.size() != 0) {
            throw new RuntimeException("Expected size 0 after clear, got " + p.size());
        }
        if (p.get("Alg.Foo") != null) {
            throw new RuntimeException("Property still present after clear");
        }

        p.clear();
        if (p.size() != 0) {
            throw new RuntimeException("clear() on empty provider changed size");
        }

        p.put("Alg.Baz", "Qux");
        if (!"Qux".equals(p.get("Alg.Baz"))) {
            throw new RuntimeException("Provider not usable after clear()");
        }

        if (!"TestProv".equals(p.getName())) {
            throw new RuntimeException("Provider name changed by clear()");
        }
    }
}
