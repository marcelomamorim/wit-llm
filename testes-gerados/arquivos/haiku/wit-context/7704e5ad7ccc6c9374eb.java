/*
 * @test
 * @summary Witup-style observable checks for Provider.putAll
 * @run main ProviderWitupTest
 */
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest {
    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Map<String, String> first = new HashMap<>();
        first.put("myProp", "v1");
        first.put("otherProp", "v2");
        p.putAll(first);
        check("v1".equals(p.getProperty("myProp")), "myProp copied by putAll");
        check("v2".equals(p.getProperty("otherProp")), "otherProp copied by putAll");

        Map<String, String> second = new HashMap<>();
        second.put("myProp", "v3");
        p.putAll(second);
        check("v3".equals(p.getProperty("myProp")), "myProp overwritten by putAll");
        check("v2".equals(p.getProperty("otherProp")), "otherProp preserved after second putAll");

        p.putAll(new HashMap<String, String>());
        check("v3".equals(p.getProperty("myProp")), "empty putAll preserves existing entries");
    }
}
