/*
 * @test
 * @summary Behavioral tests for the forEach implementation of the Arrays.asList view
 * @run main ArraysWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testVisitsElementsInOrder();
        testNullActionThrowsNPE();
        testNullElementsArePassedToAction();
        testEmptyListInvokesNoAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testVisitsElementsInOrder() {
        List<String> seen = new ArrayList<>();
        Arrays.asList("a", "b", "c").forEach(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "forEach must visit elements in array order, got " + seen);
    }

    static void testNullActionThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.asList("a").forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testNullElementsArePassedToAction() {
        List<String> seen = new ArrayList<>();
        Arrays.asList("x", null, "y").forEach(seen::add);
        check(seen.size() == 3, "expected 3 visited elements, got " + seen.size());
        check(seen.get(0).equals("x"), "first element mismatch");
        check(seen.get(1) == null, "null element must be passed to action");
        check(seen.get(2).equals("y"), "last element mismatch");
    }

    static void testEmptyListInvokesNoAction() {
        int[] count = new int[1];
        Arrays.asList(new String[0]).forEach(e -> count[0]++);
        check(count[0] == 0, "action must not be invoked for an empty array, count=" + count[0]);
    }
}
