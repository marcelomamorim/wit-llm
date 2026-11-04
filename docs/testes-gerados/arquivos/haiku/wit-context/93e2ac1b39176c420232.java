/*
 * @test
 * @summary Witup-generated tests for Properties.replace(Object, Object, Object)
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        replaceWithMatchingOldValue();
        replaceWithNonMatchingOldValue();
        replaceAbsentKeyReturnsFalse();
        nullKeyThrowsNpe();
        nullOldValueThrowsNpe();
        nullNewValueThrowsNpe();
    }

    static void replaceWithMatchingOldValue() {
        Properties p = new Properties();
        p.setProperty("k", "v1");
        check(p.replace("k", "v1", "v2"), "replace with matching old value should return true");
        check("v2".equals(p.getProperty("k")), "value should be updated to v2");
    }

    static void replaceWithNonMatchingOldValue() {
        Properties p = new Properties();
        p.setProperty("k", "v1");
        check(!p.replace("k", "x", "v2"), "replace with non-matching old value should return false");
        check("v1".equals(p.getProperty("k")), "value should remain v1");
    }

    static void replaceAbsentKeyReturnsFalse() {
        Properties p = new Properties();
        check(!p.replace("k", "v1", "v2"), "replace on absent key should return false");
        check(p.getProperty("k") == null, "absent key must not be inserted");
    }

    static void nullKeyThrowsNpe() {
        Properties p = new Properties();
        expectNpe(() -> p.replace(null, "a", "b"));
    }

    static void nullOldValueThrowsNpe() {
        Properties p = new Properties();
        p.setProperty("k", "v1");
        expectNpe(() -> p.replace("k", null, "b"));
    }

    static void nullNewValueThrowsNpe() {
        Properties p = new Properties();
        p.setProperty("k", "v1");
        expectNpe(() -> p.replace("k", "v1", null));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }
}
