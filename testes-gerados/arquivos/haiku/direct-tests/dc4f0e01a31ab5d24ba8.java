/*
 * @test
 * @summary Behavioral tests for Vector.removeIf based on the public API
 * @run main VectorWitupTest
 */
import java.util.Arrays;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testEmptyVectorReturnsFalse();
        testRemovesMatchingElementsKeepsOrder();
        testNoMatchReturnsFalseAndKeepsContent();
        testAllMatchingEmptiesVector();
        testNullFilterThrowsNullPointerException();
        testVectorFromCollectionConstructor();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyVectorReturnsFalse() {
        Vector<Integer> v = new Vector<>();
        boolean changed = v.removeIf(x -> true);
        check(!changed, "removeIf on empty vector should return false");
        check(v.isEmpty(), "vector should remain empty");
    }

    static void testRemovesMatchingElementsKeepsOrder() {
        Vector<Integer> v = new Vector<>();
        for (int i = 1; i <= 6; i++) {
            v.add(i);
        }
        boolean changed = v.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        check(v.size() == 3, "size should be 3 after removing evens");
        check(v.equals(Arrays.asList(1, 3, 5)), "remaining elements should be [1, 3, 5] in order");
    }

    static void testNoMatchReturnsFalseAndKeepsContent() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(3);
        v.add(5);
        boolean changed = v.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf with no match should return false");
        check(v.equals(Arrays.asList(1, 3, 5)), "content should be unchanged when nothing matches");
    }

    static void testAllMatchingEmptiesVector() {
        Vector<Integer> v = new Vector<>(Arrays.asList(2, 4, 6));
        boolean changed = v.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when all elements match");
        check(v.isEmpty(), "vector should be empty after all elements are removed");
        check(v.size() == 0, "size should be 0");
    }

    static void testNullFilterThrowsNullPointerException() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        boolean thrown = false;
        try {
            v.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) should throw NullPointerException");
        check(v.size() == 1 && v.get(0) == 1, "vector should be unchanged after null filter");
    }

    static void testVectorFromCollectionConstructor() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2, 3, 4));
        boolean changed = v.removeIf(x -> x > 2);
        check(changed, "removeIf should remove elements greater than 2");
        check(v.equals(Arrays.asList(1, 2)), "remaining elements should be [1, 2]");
    }
}
