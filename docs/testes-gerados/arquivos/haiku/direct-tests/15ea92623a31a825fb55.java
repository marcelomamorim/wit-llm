/*
 * @test
 * @summary ArrayList.forEach rejects a null action, visits elements in order, and detects concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyListDoesNotInvokeAction();
        testVisitsElementsInOrderIncludingNull();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean thrown = false;
        try {
            list.forEach((Consumer<? super String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testEmptyListDoesNotInvokeAction() {
        ArrayList<String> list = new ArrayList<>();
        int[] calls = {0};
        list.forEach(s -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked for empty list");
    }

    static void testVisitsElementsInOrderIncludingNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add(null);
        list.add("y");
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append(s).append(';'));
        check(sb.toString().equals("x;null;y;"),
              "unexpected visit sequence: " + sb);
    }

    static void testConcurrentModificationDetected() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int[] calls = {0};
        boolean thrown = false;
        try {
            list.forEach(e -> {
                calls[0]++;
                list.add(99);
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException");
        check(calls[0] == 1, "action should run once before modification stops loop, ran " + calls[0]);
        check(list.size() == 4, "list should contain the added element, size=" + list.size());
    }
}
