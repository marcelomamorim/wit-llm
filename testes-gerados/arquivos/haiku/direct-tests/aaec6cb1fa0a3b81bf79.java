/*
 * @test
 * @summary Verify tryAdvance on collection-backed spliterators created by Spliterators
 * @run main SpliteratorsWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testEmptyCollectionReturnsFalseWithoutInvokingAction();
        testElementsDeliveredInOrder();
        testExhaustedSpliteratorReturnsFalseWithoutInvokingAction();
        testNullActionThrowsNullPointerException();
    }

    static void testEmptyCollectionReturnsFalseWithoutInvokingAction() {
        List<String> empty = new ArrayList<>();
        Spliterator<String> s = Spliterators.spliterator(empty, 0);
        boolean[] called = new boolean[1];
        boolean result = s.tryAdvance(x -> called[0] = true);
        if (result) {
            throw new RuntimeException("Expected false for empty collection");
        }
        if (called[0]) {
            throw new RuntimeException("Action must not be invoked for empty collection");
        }
    }

    static void testElementsDeliveredInOrder() {
        List<String> source = new ArrayList<>();
        source.add("a");
        source.add("b");
        source.add("c");
        Spliterator<String> s = Spliterators.spliterator(source, 0);
        List<String> out = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (!s.tryAdvance(out::add)) {
                throw new RuntimeException("Expected true at element " + i);
            }
        }
        if (!out.equals(source)) {
            throw new RuntimeException("Unexpected elements: " + out);
        }
    }

    static void testExhaustedSpliteratorReturnsFalseWithoutInvokingAction() {
        List<String> source = new ArrayList<>();
        source.add("only");
        Spliterator<String> s = Spliterators.spliterator(source, 0);
        List<String> out = new ArrayList<>();
        if (!s.tryAdvance(out::add)) {
            throw new RuntimeException("Expected true for first element");
        }
        int[] calls = new int[1];
        boolean result = s.tryAdvance(x -> calls[0]++);
        if (result) {
            throw new RuntimeException("Expected false after exhaustion");
        }
        if (calls[0] != 0) {
            throw new RuntimeException("Action invoked after exhaustion");
        }
        if (out.size() != 1 || !"only".equals(out.get(0))) {
            throw new RuntimeException("Unexpected output: " + out);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        List<String> source = new ArrayList<>();
        source.add("x");
        Spliterator<String> s = Spliterators.spliterator(source, 0);
        try {
            s.tryAdvance(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null action");
    }
}
