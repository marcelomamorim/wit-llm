/*
 * @test
 * @summary Verifies Spliterator.tryAdvance behavior for arrays wrapped by Spliterators.spliterator, including the NullPointerException for a null action.
 * @run main SpliteratorsWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testNullActionDoesNotPreventLaterAdvance();
        testSequentialAdvanceConsumesElementsInOrder();
        testEmptyArrayReturnsFalseWithoutInvokingAction();
        testAdvanceOnExhaustedSpliteratorReturnsFalse();
    }

    static void testNullActionThrowsNullPointerException() {
        Spliterator<String> s = Spliterators.spliterator(new Object[] {"a"}, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null action");
        }
    }

    static void testNullActionDoesNotPreventLaterAdvance() {
        Spliterator<String> s = Spliterators.spliterator(new Object[] {"a", "b"}, Spliterator.ORDERED);
        try {
            s.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
        List<String> seen = new ArrayList<>();
        boolean advanced = s.tryAdvance(seen::add);
        if (!advanced) {
            throw new AssertionError("Expected tryAdvance to return true after rejected null action");
        }
        if (seen.size() != 1 || !"a".equals(seen.get(0))) {
            throw new AssertionError("Expected first element 'a' after rejected null action, got " + seen);
        }
    }

    static void testSequentialAdvanceConsumesElementsInOrder() {
        Spliterator<String> s = Spliterators.spliterator(new Object[] {"x", "y", "z"}, Spliterator.ORDERED);
        List<String> seen = new ArrayList<>();
        Consumer<String> action = seen::add;
        if (!s.tryAdvance(action)) {
            throw new AssertionError("Expected true for first element");
        }
        if (!s.tryAdvance(action)) {
            throw new AssertionError("Expected true for second element");
        }
        if (!s.tryAdvance(action)) {
            throw new AssertionError("Expected true for third element");
        }
        if (s.tryAdvance(action)) {
            throw new AssertionError("Expected false after all elements consumed");
        }
        if (!"[x, y, z]".equals(seen.toString())) {
            throw new AssertionError("Unexpected elements: " + seen);
        }
    }

    static void testEmptyArrayReturnsFalseWithoutInvokingAction() {
        Spliterator<String> s = Spliterators.spliterator(new Object[0], Spliterator.ORDERED);
        boolean[] invoked = new boolean[1];
        boolean result = s.tryAdvance(e -> invoked[0] = true);
        if (result) {
            throw new AssertionError("Expected false for empty array");
        }
        if (invoked[0]) {
            throw new AssertionError("Action must not be invoked for empty array");
        }
    }

    static void testAdvanceOnExhaustedSpliteratorReturnsFalse() {
        Spliterator<String> s = Spliterators.spliterator(new Object[] {"only"}, Spliterator.ORDERED);
        Consumer<String> noop = e -> { };
        if (!s.tryAdvance(noop)) {
            throw new AssertionError("Expected true for the single element");
        }
        if (s.tryAdvance(noop)) {
            throw new AssertionError("Expected false on exhausted spliterator");
        }
        if (s.tryAdvance(noop)) {
            throw new AssertionError("Expected false on repeated call after exhaustion");
        }
    }
}
