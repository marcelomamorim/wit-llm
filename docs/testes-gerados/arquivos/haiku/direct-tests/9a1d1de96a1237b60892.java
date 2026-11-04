/*
 * @test
 * @summary Tests observable behavior of Properties.replace(Object, Object)
 * @run main PropertiesWitupTest
 */
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceAbsentKeyDoesNotInsert();
        testReplacePresentKeyReturnsPreviousAndUpdates();
        testReplaceNonStringKeyAndValue();
        testReplaceIgnoresDefaults();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceAbsentKeyDoesNotInsert() {
        Properties p = new Properties();
        Object previous = p.replace("k", "v");
        check(previous == null, "replace on absent key should return null");
        check(p.get("k") == null, "replace on absent key should not insert the entry");
        check(p.size() == 0, "size should remain 0 after replace on absent key");
    }

    static void testReplacePresentKeyReturnsPreviousAndUpdates() {
        Properties p = new Properties();
        p.put("k", "v1");
        Object previous = p.replace("k", "v2");
        check("v1".equals(previous), "replace should return previous value");
        check("v2".equals(p.get("k")), "replace should update the value");
        check(p.size() == 1, "size should be unchanged after replacing an existing key");
    }

    static void testReplaceNonStringKeyAndValue() {
        Properties p = new Properties();
        Integer key = Integer.valueOf(1);
        p.put(key, "a");
        Object previous = p.replace(key, "b");
        check("a".equals(previous), "replace should return previous non-String value");
        check("b".equals(p.get(key)), "replace should update non-String key entry");
    }

    static void testReplaceIgnoresDefaults() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "dv");
        Properties p = new Properties(defaults);
        Object previous = p.replace("d", "x");
        check(previous == null, "replace should not return a value from defaults");
        check("dv".equals(p.getProperty("d")), "replace on absent key should not affect defaults lookup");
    }
}
