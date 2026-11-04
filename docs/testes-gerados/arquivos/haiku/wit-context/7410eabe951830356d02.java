/*
 * @test
 * @summary Verify Provider.remove returns the removed value, clears the property, and returns null when the key is absent
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test provider");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        p.put("Test.Alg", "ImplA");
        Object removed = p.remove("Test.Alg");
        if (!"ImplA".equals(removed)) {
            throw new RuntimeException("expected removed value ImplA, got " + removed);
        }
        if (p.containsKey("Test.Alg")) {
            throw new RuntimeException("property still present after remove");
        }

        Object again = p.remove("Test.Alg");
        if (again != null) {
            throw new RuntimeException("removing absent key should return null, got " + again);
        }

        Object missing = p.remove("Missing.Alg");
        if (missing != null) {
            throw new RuntimeException("removing never-added key should return null, got " + missing);
        }
    }
}
