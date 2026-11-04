/*
 * @test
 * @summary Behavioral tests for Vector.replaceAll(UnaryOperator): element replacement, empty vector, null operator, and concurrent modification detection
 * @run main VectorWitupTest
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testReplaceAllTransformsElements();
        testEmptyVectorDoesNotInvokeOperator();
        testNullOperatorThrowsNullPointerException();
        testOperatorStructuralModificationThrowsConcurrentModificationException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceAllTransformsElements() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        v.replaceAll(s -> s.toUpperCase());
        check(v.size() == 3, "size changed after replaceAll");
        check(v.get(0).equals("A"), "element 0 not replaced");
        check(v.get(1).equals("B"), "element 1 not replaced");
        check(v.get(2).equals("C"), "element 2 not replaced");
    }

    static void testEmptyVectorDoesNotInvokeOperator() {
        Vector<Integer> v = new Vector<>();
        int[] calls = {0};
        v.replaceAll(x -> {
            calls[0]++;
            return x;
        });
        check(calls[0] == 0, "operator invoked on empty vector");
        check(v.isEmpty(), "empty vector became non-empty");
    }

    static void testNullOperatorThrowsNullPointerException() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2));
        boolean thrown = false;
        try {
            v.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator");
    }

    static void testOperatorStructuralModificationThrowsConcurrentModificationException() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            v.replaceAll(x -> {
                v.add(99);
                return x * 2;
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException when operator modifies vector");
    }
}
