/*
 * @test
 * @summary Behavioral tests for ArrayList.replaceAll(UnaryOperator)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplacesAllElements();
        testEmptyListDoesNotInvokeOperator();
        testNullElementsArePassedToOperator();
        testOperatorIsCalledInIndexOrder();
        testReplaceAllIncrementsModCount();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplacesAllElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list.replaceAll(s -> s.toUpperCase());
        List<String> expected = Arrays.asList("A", "B", "C");
        check(list.equals(expected), "replaceAll should replace every element: " + list);
    }

    static void testEmptyListDoesNotInvokeOperator() {
        ArrayList<String> list = new ArrayList<>();
        final int[] calls = {0};
        list.replaceAll(s -> {
            calls[0]++;
            return s;
        });
        check(calls[0] == 0, "operator must not be invoked on empty list");
        check(list.isEmpty(), "empty list must remain empty");
    }

    static void testNullElementsArePassedToOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("x");
        list.replaceAll(s -> s == null ? "NULL" : s + "!");
        List<String> expected = Arrays.asList("NULL", "x!");
        check(list.equals(expected), "null element should be passed to operator: " + list);
    }

    static void testOperatorIsCalledInIndexOrder() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> seen = new ArrayList<>();
        list.replaceAll(x -> {
            seen.add(x);
            return x * 10;
        });
        check(seen.equals(Arrays.asList(1, 2, 3)), "operator must be applied in index order: " + seen);
        check(list.equals(Arrays.asList(10, 20, 30)), "unexpected result: " + list);
    }

    static void testReplaceAllIncrementsModCount() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = list.iterator();
        list.replaceAll(x -> x + 1);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException after replaceAll");
        } catch (ConcurrentModificationException expected) {
            // fail-fast behaviour observed through the public iterator
        }
    }
}
