/*
 * @test
 * @summary Verifies observable return values of Provider.put for new and replaced properties
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTest", "1.0", "witup info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Object first = p.put("customKey", "value1");
        if (first != null) {
            throw new RuntimeException("Expected null for new key, got: " + first);
        }

        Object second = p.put("customKey", "value2");
        if (!"value1".equals(second)) {
            throw new RuntimeException("Expected previous value value1, got: " + second);
        }

        if (!"value2".equals(p.get("customKey"))) {
            throw new RuntimeException("Expected stored value value2, got: " + p.get("customKey"));
        }

        Object third = p.put("customKey", "value2");
        if (!"value2".equals(third)) {
            throw new RuntimeException("Expected previous value value2 on same-value put, got: " + third);
        }

        Object other = p.put("otherKey", "otherValue");
        if (other != null) {
            throw new RuntimeException("Expected null for another new key, got: " + other);
        }
        if (!"value2".equals(p.get("customKey"))) {
            throw new RuntimeException("Put on another key altered customKey");
        }
        if (!"otherValue".equals(p.get("otherKey"))) {
            throw new RuntimeException("Expected stored value otherValue, got: " + p.get("otherKey"));
        }
    }
}
