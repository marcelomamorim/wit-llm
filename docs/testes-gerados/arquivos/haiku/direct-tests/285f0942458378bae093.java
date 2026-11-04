/*
 * @test
 * @summary Behavioral tests for Vector.retainAll(Collection)
 * @run main VectorWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemovesNonMatchingElements();
        testNoChangeReturnsFalse();
        testEmptyCollectionClearsVector();
        testEmptyVectorReturnsFalse();
        testDuplicatesAreRetained();
        testNullElementsHandled();
        testNullArgumentThrowsNpe();
        testNullArgumentLeavesVectorUnchanged();
        testSingletonCollection();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRemovesNonMatchingElements() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2, 3, 4));
        boolean changed = v.retainAll(Arrays.asList(2, 4));
        check(changed, "expected true when elements removed");
        check(v.equals(Arrays.asList(2, 4)), "unexpected contents: " + v);
    }

    static void testNoChangeReturnsFalse() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2));
        boolean changed = v.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "expected false when nothing removed");
        check(v.equals(Arrays.asList(1, 2)), "unexpected contents: " + v);
    }

    static void testEmptyCollectionClearsVector() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2));
        boolean changed = v.retainAll(new ArrayList<Integer>());
        check(changed, "expected true when clearing non-empty vector");
        check(v.isEmpty(), "expected empty vector, got: " + v);
    }

    static void testEmptyVectorReturnsFalse() {
        Vector<Integer> v = new Vector<>();
        boolean changed = v.retainAll(Arrays.asList(1, 2));
        check(!changed, "expected false on empty vector");
        check(v.isEmpty(), "vector should remain empty");
    }

    static void testDuplicatesAreRetained() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 1, 2));
        boolean changed = v.retainAll(Arrays.asList(1));
        check(changed, "expected true when 2 removed");
        check(v.equals(Arrays.asList(1, 1)), "unexpected contents: " + v);
    }

    static void testNullElementsHandled() {
        Vector<String> v = new Vector<>(Arrays.asList("a", null, "b"));
        List<String> keep = Arrays.<String>asList(null, "b");
        boolean changed = v.retainAll(keep);
        check(changed, "expected true when \"a\" removed");
        check(v.equals(Arrays.asList(null, "b")), "unexpected contents: " + v);
    }

    static void testNullArgumentThrowsNpe() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2));
        boolean thrown = false;
        try {
            v.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null argument");
    }

    static void testNullArgumentLeavesVectorUnchanged() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2));
        try {
            v.retainAll(null);
        } catch (NullPointerException e) {
            // expected
        }
        check(v.equals(Arrays.asList(1, 2)), "vector changed after NPE: " + v);
    }

    static void testSingletonCollection() {
        Vector<Integer> v = new Vector<>(Arrays.asList(3, 5, 3));
        boolean changed = v.retainAll(Collections.singleton(3));
        check(changed, "expected true when 5 removed");
        check(v.equals(Arrays.asList(3, 3)), "unexpected contents: " + v);
    }
}
