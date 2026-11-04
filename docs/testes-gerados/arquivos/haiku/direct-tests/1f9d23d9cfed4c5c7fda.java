/*
 * @test
 * @summary Verifies that Hashtable.contains(Object) reports presence of values, delegating to containsValue semantics
 * @run main HashtableWitupTest
 */

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        emptyTableContainsNothing();
        containsMatchesValuesNotKeys();
        containsUsesEqualsNotIdentity();
        containsReflectsRemoval();
        containsAfterReplacingValue();
        containsOnCopiedTable();
    }

    static void emptyTableContainsNothing() {
        Hashtable<String, Integer> h = new Hashtable<>();
        check(!h.contains(1), "empty table must not contain 1");
        check(!h.contains("x"), "empty table must not contain \"x\"");
    }

    static void containsMatchesValuesNotKeys() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        h.put("two", 2);
        check(h.contains(1), "value 1 must be found");
        check(h.contains(Integer.valueOf(2)), "value 2 must be found");
        check(!h.contains(3), "absent value 3 must not be found");
        check(!h.contains("one"), "a key must not be reported as a value");
    }

    static void containsUsesEqualsNotIdentity() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("k", new String("v"));
        check(h.contains("v"), "equal but distinct value must be found");
        check(!h.contains("V"), "different-case value must not be found");
    }

    static void containsReflectsRemoval() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        check(h.contains(1), "value must be present before removal");
        h.remove("one");
        check(!h.contains(1), "value must not be present after removal");
    }

    static void containsAfterReplacingValue() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 10);
        h.put("a", 20);
        check(!h.contains(10), "replaced value 10 must no longer be present");
        check(h.contains(20), "new value 20 must be present");
    }

    static void containsOnCopiedTable() {
        Map<String, Integer> source = new HashMap<>();
        source.put("x", 5);
        Hashtable<String, Integer> h = new Hashtable<>(source);
        check(h.contains(5), "value copied from map must be found");
        check(!h.contains(6), "absent value must not be found in copied table");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
