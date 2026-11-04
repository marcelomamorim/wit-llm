/*
 * @test
 * @summary Verifies observable behavior of Provider.remove(Object, Object): conditional removal, value mismatch, and missing keys
 * @run main ProviderWitupTest
 */
import java.security.Provider;

public class ProviderWitupTest {

    private static final class TestProvider extends Provider {
        TestProvider() {
            super("WitupTest", "1.0", "test provider");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();
        p.put("TestKey", "v1");

        check(!p.remove("TestKey", "other"),
              "remove with mismatched value must return false");
        check("v1".equals(p.get("TestKey")),
              "mapping must be preserved after mismatched remove");

        check(p.remove("TestKey", "v1"),
              "remove with matching key and value must return true");
        check(p.get("TestKey") == null,
              "mapping must be absent after successful remove");

        check(!p.remove("TestKey", "v1"),
              "second remove of the same mapping must return false");
        check(!p.remove("MissingKey", "v1"),
              "remove of a missing key must return false");
    }
}
