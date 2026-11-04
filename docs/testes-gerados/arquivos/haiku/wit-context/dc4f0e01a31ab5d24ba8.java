/*
 * @test
 * @summary Tests Vector.removeIf null filter handling and basic predicate removal behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Predicate;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testNullFilterDoesNotModifyVector();
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyVectorReturnsFalse();
    }

    static void testNullFilterThrowsNPE() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        boolean thrown = false;
        try {
            v.removeIf((Predicate<Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null filter");
        }
    }

    static void testNullFilterDoesNotModifyVector() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        try {
            v.removeIf((Predicate<Integer>) null);
        } catch (NullPointerException e) {
            // expected
        }
        if (!"[1, 2]".equals(v.toString())) {
            throw new AssertionError("vector modified after null filter: " + v);
        }
    }

    static void testRemovesMatchingElements() {
        Vector<Integer> v = new Vector<>();
        for (int i = 1; i <= 6; i++) {
            v.add(i);
        }
        boolean changed = v.removeIf(x -> x % 2 == 0);
        if (!changed) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (!"[1, 3, 5]".equals(v.toString())) {
            throw new AssertionError("unexpected contents: " + v);
        }
    }

    static void testNoMatchReturnsFalse() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(3);
        boolean changed = v.removeIf(x -> x % 2 == 0);
        if (changed) {
            throw new AssertionError("removeIf should return false when nothing matches");
        }
        if (!"[1, 3]".equals(v.toString())) {
            throw new AssertionError("unexpected contents: " + v);
        }
    }

    static void testEmptyVectorReturnsFalse() {
        Vector<Integer> v = new Vector<>();
        boolean changed = v.removeIf(x -> true);
        if (changed) {
            throw new AssertionError("removeIf on empty vector should return false");
        }
        if (!v.isEmpty()) {
            throw new AssertionError("vector should remain empty");
        }
    }
}
