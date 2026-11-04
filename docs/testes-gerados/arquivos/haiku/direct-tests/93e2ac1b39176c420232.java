/*
 * @test
 * @summary Behavioral tests for Properties.replace(Object, Object, Object)
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAbsentKeyReturnsFalseAndDoesNotInsert() {
        Properties p = new Properties();
        boolean r = p.replace("missing", "old", "new");
        check(!r, "replace on absent key must return false");
        check(p.get("missing") == null, "absent key must not be inserted");
        check(p.size() == 0, "size must remain 0");
    }

    static void testMatchingOldValueReplaces() {
        Properties p = new Properties();
        p.setProperty("k", "v1");
        boolean r = p.replace("k", "v1", "v2");
        check(r, "replace with matching old value must return true");
        check("v2".equals(p.get("k")), "value must be updated to v2");
    }

    static void testMismatchingOldValueLeavesValue() {
        Properties p = new Properties();
        p.setProperty("k", "v1");
        boolean r = p.replace("k", "other", "v2");
        check(!r, "replace with mismatching old value must return false");
        check("v1".equals(p.get("k")), "value must remain v1");
    }

    static void testOldValueComparedByEquals() {
        Properties p = new Properties();
        p.setProperty("k", "abc");
        String probe = new String("ab") + "c";
        boolean r = p.replace("k", probe, "xyz");
        check(r, "equal (not identical) old value must match");
        check("xyz".equals(p.get("k")), "value must be replaced by xyz");
    }

    static void testNonStringValuesViaPut() {
        Properties p = new Properties();
        p.put("n", Integer.valueOf(1));
        boolean r = p.replace("n", Integer.valueOf(1), Integer.valueOf(2));
        check(r, "replace of non-String value with equal value must return true");
        check(Integer.valueOf(2).equals(p.get("n")), "value must be Integer 2");
    }

    static void testDefaultsAreNotConsulted() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "dv");
        Properties p = new Properties(defaults);
        boolean r = p.replace("d", "dv", "nv");
        check(!r, "replace must not act on defaults; expected false");
        check(p.get("d") == null, "own map must not gain key from defaults");
        check("dv".equals(p.getProperty("d")), "defaults must remain unchanged");
    }

    static void testRepeatedReplaceSequence() {
        Properties p = new Properties();
        p.setProperty("s", "a");
        check(p.replace("s", "a", "b"), "first replace must succeed");
        check(!p.replace("s", "a", "c"), "second replace with stale old value must fail");
        check(p.replace("s", "b", "c"), "third replace with current value must succeed");
        check("c".equals(p.get("s")), "final value must be c");
    }

    public static void main(String[] args) {
        testAbsentKeyReturnsFalseAndDoesNotInsert();
        testMatchingOldValueReplaces();
        testMismatchingOldValueLeavesValue();
        testOldValueComparedByEquals();
        testNonStringValuesViaPut();
        testDefaultsAreNotConsulted();
        testRepeatedReplaceSequence();
    }
}
