/*
 * @test
 * @summary Witup-derived checks for Provider.get(Object) on an initialized provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static final class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test");
        }
    }

    public static void main(String[] args) {
        Provider p = new TestProvider();

        Object missing = p.get("no-such-key");
        if (missing != null) {
            throw new RuntimeException("expected null for missing key, got: " + missing);
        }

        Object again = p.get("another-missing-key");
        if (again != null) {
            throw new RuntimeException("expected null for second missing key, got: " + again);
        }

        try {
            p.get(null);
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Hashtable-based lookup rejects null keys after the initialization check
        }
    }
}
