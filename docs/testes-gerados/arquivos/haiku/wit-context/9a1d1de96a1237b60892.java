/*
 * @test
 * @summary Tests observable behavior of Properties.replace(Object, Object), including the NullPointerException path for null key or value
 * @run main PropertiesWitupTest
 */
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKeyReturnsPreviousValue();
        testReplaceAbsentKeyReturnsNullAndDoesNotInsert();
        testNullKeyThrowsNPEAndKeepsState();
        testNullValueThrowsNPEAndKeepsState();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceExistingKeyReturnsPreviousValue() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        Object previous = p.replace("a", "2");
        check("1".equals(previous), "expected previous value 1 but was " + previous);
        check("2".equals(p.getProperty("a")), "expected updated value 2");
        check(p.size() == 1, "expected size 1 but was " + p.size());
    }

    static void testReplaceAbsentKeyReturnsNullAndDoesNotInsert() {
        Properties p = new Properties();
        Object previous = p.replace("missing", "v");
        check(previous == null, "expected null for absent key but was " + previous);
        check(!p.containsKey("missing"), "replace must not insert absent key");
        check(p.size() == 0, "expected empty properties but size was " + p.size());
    }

    static void testNullKeyThrowsNPEAndKeepsState() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        try {
            p.replace(null, "x");
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
        check("1".equals(p.getProperty("a")), "state changed after failed replace");
        check(p.size() == 1, "size changed after failed replace");
    }

    static void testNullValueThrowsNPEAndKeepsState() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        try {
            p.replace("a", null);
            throw new RuntimeException("expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
        check("1".equals(p.getProperty("a")), "state changed after failed replace");
        check(p.size() == 1, "size changed after failed replace");
    }
}
