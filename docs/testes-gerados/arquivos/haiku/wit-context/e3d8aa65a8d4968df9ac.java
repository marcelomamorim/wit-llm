/*
 * @test
 * @summary Behavioral tests for Provider.getOrDefault covering present, absent, replaced and removed keys
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test provider");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Object absent = p.getOrDefault("Alg.Missing", "dflt");
        check("dflt".equals(absent), "absent key must return default value");

        Object absentNull = p.getOrDefault("Alg.Missing", null);
        check(absentNull == null, "absent key with null default must return null");

        p.put("Alg.Test", "value1");
        Object present = p.getOrDefault("Alg.Test", "dflt");
        check("value1".equals(present), "present key must return stored value, not default");

        p.put("Alg.Test", "value2");
        Object replaced = p.getOrDefault("Alg.Test", "dflt");
        check("value2".equals(replaced), "getOrDefault must reflect replaced value");

        p.remove("Alg.Test");
        Object removed = p.getOrDefault("Alg.Test", "dflt");
        check("dflt".equals(removed), "removed key must return default value");
    }
}
