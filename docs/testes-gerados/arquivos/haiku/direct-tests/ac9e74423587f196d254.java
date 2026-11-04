/*
 * @test
 * @summary Tests Vector.forEach: in-order visiting, empty vector, null action and concurrent modification
 * @run main VectorWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public class VectorWitupTest {

    public static void main(String[] args) {
        testVisitsElementsInOrder();
        testEmptyVectorInvokesNoAction();
        testNullActionThrowsNullPointerException();
        testStructuralAddDuringForEachThrowsCME();
        testStructuralRemoveDuringForEachThrowsCME();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testVisitsElementsInOrder() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        List<String> seen = new ArrayList<>();
        v.forEach(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "forEach did not visit elements in order: " + seen);
    }

    static void testEmptyVectorInvokesNoAction() {
        Vector<String> v = new Vector<>();
        int[] count = {0};
        v.forEach(s -> count[0]++);
        check(count[0] == 0, "action invoked on empty vector: " + count[0]);
    }

    static void testNullActionThrowsNullPointerException() {
        Vector<String> v = new Vector<>(Arrays.asList("a"));
        Consumer<String> nullAction = null;
        try {
            v.forEach(nullAction);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testStructuralAddDuringForEachThrowsCME() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        try {
            v.forEach(s -> v.add("x"));
        } catch (ConcurrentModificationException expected) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException after add");
    }

    static void testStructuralRemoveDuringForEachThrowsCME() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        try {
            v.forEach(s -> v.remove(0));
        } catch (ConcurrentModificationException expected) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException after remove");
    }
}
