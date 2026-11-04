/*
 * @test
 * @summary Verifies Vector.retainAll null handling and bulk retention behavior
 * @run main VectorWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRetainsOnlyContainedElements();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollectionClearsVector();
        testEmptyVectorReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        boolean thrown = false;
        try {
            v.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(v.size() == 2, "vector must be unchanged after NPE");
        check("a".equals(v.get(0)) && "b".equals(v.get(1)), "contents must be unchanged after NPE");
    }

    static void testRetainsOnlyContainedElements() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c", "a"));
        boolean changed = v.retainAll(Arrays.asList("a", "c"));
        check(changed, "retainAll must return true when elements are removed");
        check(v.size() == 3, "expected size 3 but was " + v.size());
        check("a".equals(v.get(0)), "index 0 should be a");
        check("c".equals(v.get(1)), "index 1 should be c");
        check("a".equals(v.get(2)), "index 2 should be a");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        boolean changed = v.retainAll(Arrays.asList("a", "b", "x"));
        check(!changed, "retainAll must return false when nothing is removed");
        check(v.size() == 2, "size must remain 2");
    }

    static void testEmptyCollectionClearsVector() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        boolean changed = v.retainAll(Collections.<String>emptyList());
        check(changed, "retainAll with empty collection must return true");
        check(v.isEmpty(), "vector must be empty after retaining nothing");
    }

    static void testEmptyVectorReturnsFalse() {
        Vector<String> v = new Vector<>();
        boolean changed = v.retainAll(Arrays.asList("a"));
        check(!changed, "retainAll on empty vector must return false");
        check(v.isEmpty(), "vector must remain empty");
    }
}
