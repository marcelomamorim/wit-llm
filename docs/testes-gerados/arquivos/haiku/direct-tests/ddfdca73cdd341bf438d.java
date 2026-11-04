/*
 * @test
 * @summary Verify tryAdvance behavior of array-backed spliterators created by Spliterators.spliterator(Object[], int)
 * @run main SpliteratorsWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testAdvanceConsumesElementsInOrder();
        testExhaustedReturnsFalse();
        testEmptyArrayReturnsFalseWithoutAccepting();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testAdvanceConsumesElementsInOrder() {
        Object[] data = {"a", "b"};
        Spliterator<String> sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        List<String> seen = new ArrayList<>();

        check(sp.tryAdvance(seen::add), "first tryAdvance should return true");
        check(seen.size() == 1 && seen.get(0).equals("a"), "first element should be \"a\"");

        check(sp.tryAdvance(seen::add), "second tryAdvance should return true");
        check(seen.size() == 2 && seen.get(1).equals("b"), "second element should be \"b\"");
    }

    static void testExhaustedReturnsFalse() {
        Object[] data = {"x"};
        Spliterator<String> sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        List<String> seen = new ArrayList<>();

        check(sp.tryAdvance(seen::add), "first tryAdvance should return true");
        check(!sp.tryAdvance(seen::add), "tryAdvance after exhaustion should return false");
        check(!sp.tryAdvance(seen::add), "repeated tryAdvance after exhaustion should return false");
        check(seen.size() == 1, "action must not be invoked after exhaustion");
    }

    static void testEmptyArrayReturnsFalseWithoutAccepting() {
        Object[] empty = new Object[0];
        Spliterator<String> sp = Spliterators.spliterator(empty, Spliterator.ORDERED);
        int[] calls = {0};

        check(!sp.tryAdvance(e -> calls[0]++), "tryAdvance on empty array should return false");
        check(calls[0] == 0, "action must not be invoked for empty array");
    }

    static void testNullActionThrowsNPE() {
        Object[] data = {"a"};
        Spliterator<String> sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) should throw NullPointerException");
    }
}
