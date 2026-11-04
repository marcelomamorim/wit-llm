/*
 * @test
 * @summary Witup-derived checks for Properties.putIfAbsent null handling and observable semantics
 * @run main PropertiesWitupTest
 */

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNullPointerException();
        testNullValueThrowsNullPointerException();
        testAbsentKeyInsertsValueAndReturnsNull();
        testPresentKeyKeepsExistingValue();
    }

    static void testNullKeyThrowsNullPointerException() {
        Properties p = new Properties();
        try {
            p.putIfAbsent(null, "v");
            throw new AssertionError("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
        if (!p.isEmpty()) {
            throw new AssertionError("map must remain empty after failed putIfAbsent");
        }
    }

    static void testNullValueThrowsNullPointerException() {
        Properties p = new Properties();
        try {
            p.putIfAbsent("k", null);
            throw new AssertionError("expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
        if (p.containsKey("k")) {
            throw new AssertionError("key must not be inserted when value is null");
        }
    }

    static void testAbsentKeyInsertsValueAndReturnsNull() {
        Properties p = new Properties();
        Object previous = p.putIfAbsent("k", "v1");
        if (previous != null) {
            throw new AssertionError("expected null for absent key, got " + previous);
        }
        if (!"v1".equals(p.getProperty("k"))) {
            throw new AssertionError("value not stored");
        }
    }

    static void testPresentKeyKeepsExistingValue() {
        Properties p = new Properties();
        p.setProperty("k", "old");
        Object previous = p.putIfAbsent("k", "new");
        if (!"old".equals(previous)) {
            throw new AssertionError("expected existing value \"old\", got " + previous);
        }
        if (!"old".equals(p.getProperty("k"))) {
            throw new AssertionError("existing value must not be replaced");
        }
    }
}
