/*
 * @test
 * @summary Behavioral tests for Properties.forEach(BiConsumer) over own entries, defaults and non-String entries
 * @run main PropertiesWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testEmptyPropertiesDoesNotInvokeAction();
        testStringEntriesAreVisitedExactlyOnce();
        testDefaultsAreNotVisited();
        testNonStringEntriesAreVisited();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyPropertiesDoesNotInvokeAction() {
        Properties p = new Properties();
        int[] count = {0};
        p.forEach((k, v) -> count[0]++);
        check(count[0] == 0, "action must not be invoked for empty Properties");
    }

    static void testStringEntriesAreVisitedExactlyOnce() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        p.setProperty("b", "2");
        Map<Object, Object> seen = new HashMap<>();
        int[] calls = {0};
        p.forEach((k, v) -> {
            calls[0]++;
            check(!seen.containsKey(k), "key visited more than once: " + k);
            seen.put(k, v);
        });
        check(calls[0] == 2, "expected 2 invocations but got " + calls[0]);
        check(seen.size() == 2, "expected 2 entries seen");
        check("1".equals(seen.get("a")), "wrong value for key a");
        check("2".equals(seen.get("b")), "wrong value for key b");
    }

    static void testDefaultsAreNotVisited() {
        Properties defaults = new Properties();
        defaults.setProperty("dk", "dv");
        Properties p = new Properties(defaults);
        p.setProperty("k", "v");
        check("dv".equals(p.getProperty("dk")), "default must be reachable via getProperty");
        Map<Object, Object> seen = new HashMap<>();
        p.forEach((k, v) -> seen.put(k, v));
        check(seen.size() == 1, "only own entries must be visited, got " + seen.size());
        check(seen.containsKey("k"), "own entry k must be visited");
        check(!seen.containsKey("dk"), "default entry dk must not be visited");
    }

    static void testNonStringEntriesAreVisited() {
        Properties p = new Properties();
        p.put(Integer.valueOf(1), Integer.valueOf(2));
        Map<Object, Object> seen = new HashMap<>();
        p.forEach((k, v) -> seen.put(k, v));
        check(seen.size() == 1, "expected one non-String entry");
        check(Integer.valueOf(2).equals(seen.get(Integer.valueOf(1))),
              "wrong value for non-String entry");
    }
}
