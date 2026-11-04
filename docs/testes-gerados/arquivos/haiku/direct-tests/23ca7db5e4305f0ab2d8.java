/*
 * @test
 * @summary Tests observable behavior of Hashtable.replaceAll(BiFunction)
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplacesAllValues();
        testNullFunctionOnEmptyTableThrowsNPE();
        testNullFunctionOnNonEmptyTableThrowsNPE();
        testNullResultThrowsNPE();
        testEmptyTableDoesNotInvokeFunction();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplacesAllValues() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.put("b", 2);
        h.replaceAll((k, v) -> v + 10);
        check(h.size() == 2, "size changed");
        check(h.get("a") == 11, "a not replaced: " + h.get("a"));
        check(h.get("b") == 12, "b not replaced: " + h.get("b"));
    }

    static void testNullFunctionOnEmptyTableThrowsNPE() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null function on empty table");
    }

    static void testNullFunctionOnNonEmptyTableThrowsNPE() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null function");
    }

    static void testNullResultThrowsNPE() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.replaceAll((k, v) -> null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException when function returns null");
    }

    static void testEmptyTableDoesNotInvokeFunction() {
        Hashtable<String, Integer> h = new Hashtable<>();
        int[] calls = {0};
        h.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function invoked on empty table: " + calls[0]);
        check(h.isEmpty(), "empty table became non-empty");
    }

    static void testConcurrentModificationDetected() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.replaceAll((k, v) -> {
                h.put("new-" + k, 0);
                return v;
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
