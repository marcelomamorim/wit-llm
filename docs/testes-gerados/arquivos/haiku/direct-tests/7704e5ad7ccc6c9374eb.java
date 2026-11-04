/*
 * @test
 * @summary Verifies that Provider.putAll makes the given String entries observable through getProperty and overwrites existing values
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "test provider");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPutAllAddsEntries() {
        TestProvider p = new TestProvider();
        Map<String, String> m = new HashMap<>();
        m.put("Test.Key1", "value1");
        m.put("Test.Key2", "value2");
        p.putAll(m);
        check("value1".equals(p.getProperty("Test.Key1")),
              "putAll did not expose Test.Key1");
        check("value2".equals(p.getProperty("Test.Key2")),
              "putAll did not expose Test.Key2");
    }

    static void testPutAllOverwritesExisting() {
        TestProvider p = new TestProvider();
        p.setProperty("Test.Over", "old");
        Map<String, String> m = new HashMap<>();
        m.put("Test.Over", "new");
        p.putAll(m);
        check("new".equals(p.getProperty("Test.Over")),
              "putAll did not overwrite existing value");
    }

    static void testPutAllEmptyMapLeavesProviderUnchanged() {
        TestProvider p = new TestProvider();
        p.setProperty("Test.Keep", "kept");
        p.putAll(new HashMap<String, String>());
        check("kept".equals(p.getProperty("Test.Keep")),
              "putAll with empty map altered existing property");
        check(p.getProperty("Test.Missing") == null,
              "putAll with empty map created unexpected property");
    }

    public static void main(String[] args) throws Exception {
        testPutAllAddsEntries();
        testPutAllOverwritesExisting();
        testPutAllEmptyMapLeavesProviderUnchanged();
    }
}
