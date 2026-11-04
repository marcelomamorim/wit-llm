/*
 * @test
 * @summary Verifies that Provider.keySet() exposes the stored keys and returns an unmodifiable view
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTest", "1.0", "info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();
        p.put("Test.Key", "Value");

        Set<Object> keys = p.keySet();
        if (!keys.contains("Test.Key")) {
            throw new RuntimeException("keySet() does not contain the stored key");
        }

        try {
            keys.add("Other.Key");
            throw new RuntimeException("keySet() returned a modifiable set (add)");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        try {
            keys.remove("Test.Key");
            throw new RuntimeException("keySet() returned a modifiable set (remove)");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        if (!p.keySet().contains("Test.Key")) {
            throw new RuntimeException("Stored key was removed through keySet()");
        }

        p.put("Test.Second", "Value2");
        if (!p.keySet().contains("Test.Second")) {
            throw new RuntimeException("keySet() does not reflect newly added key");
        }
    }
}
