/*
 * @test
 * @summary Witup-derived tests for Hashtable.replaceAll null checks, value replacement and concurrent modification detection
 * @run main HashtableWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testNullFunctionOnEmptyTable();
        testNullFunctionOnNonEmptyTable();
        testReplacesValues();
        testNullResultRejected();
        testStructuralModificationDetected();
    }

    static void testNullFunctionOnEmptyTable() {
        Hashtable<String, Integer> h = new Hashtable<>();
        try {
            h.replaceAll(null);
            throw new AssertionError("expected NullPointerException for null function on empty table");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullFunctionOnNonEmptyTable() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        try {
            h.replaceAll(null);
            throw new AssertionError("expected NullPointerException for null function");
        } catch (NullPointerException expected) {
        }
        if (h.get("a") != 1) {
            throw new AssertionError("value changed after rejected null function");
        }
    }

    static void testReplacesValues() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.put("b", 2);
        h.replaceAll((k, v) -> v * 10);
        if (h.size() != 2) {
            throw new AssertionError("unexpected size: " + h.size());
        }
        if (h.get("a") != 10 || h.get("b") != 20) {
            throw new AssertionError("values not replaced: a=" + h.get("a") + " b=" + h.get("b"));
        }
    }

    static void testNullResultRejected() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        try {
            h.replaceAll((k, v) -> null);
            throw new AssertionError("expected NullPointerException when function returns null");
        } catch (NullPointerException expected) {
        }
    }

    static void testStructuralModificationDetected() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        try {
            h.replaceAll((k, v) -> {
                h.put("extra", 0);
                return v;
            });
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
