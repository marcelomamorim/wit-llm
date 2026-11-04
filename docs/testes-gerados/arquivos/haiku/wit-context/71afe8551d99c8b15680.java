/*
 * @test
 * @summary Verifies that Provider.clear() removes previously stored provider properties and is idempotent
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", 1.0, "test info");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();
        check("WitupTestProvider".equals(p.getName()), "unexpected provider name");

        p.put("Test.Prop", "value");
        check("value".equals(p.get("Test.Prop")), "property was not stored before clear");

        p.clear();
        check(p.get("Test.Prop") == null, "property still present after clear");
        check(p.isEmpty(), "provider not empty after clear");

        p.clear();
        check(p.isEmpty(), "provider not empty after second clear");
        check("WitupTestProvider".equals(p.getName()), "name changed by clear");
    }
}
