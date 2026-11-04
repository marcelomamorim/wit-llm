/*
 * @test
 * @summary Checks Spliterators iterator-backed tryAdvance: null action rejection, element consumption and exhaustion.
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testNullActionThrowsEvenWhenExhausted();
        testTryAdvanceConsumesElementsInOrder();
        testTryAdvanceReturnsFalseWhenExhausted();
        testEmptyIteratorReturnsFalseWithoutInvokingAction();
    }

    private static Spliterator<String> newSpliterator(List<String> source) {
        Iterator<String> it = source.iterator();
        return Spliterators.spliterator(it, source.size(), 0);
    }

    private static void testNullActionThrowsNullPointerException() {
        Spliterator<String> s = newSpliterator(listOf("a", "b"));
        try {
            s.tryAdvance(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null action");
    }

    private static void testNullActionThrowsEvenWhenExhausted() {
        Spliterator<String> s = newSpliterator(new ArrayList<String>());
        try {
            s.tryAdvance(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null action on exhausted spliterator");
    }

    private static void testTryAdvanceConsumesElementsInOrder() {
        Spliterator<String> s = newSpliterator(listOf("x", "y", "z"));
        List<String> seen = new ArrayList<>();
        boolean r1 = s.tryAdvance(seen::add);
        boolean r2 = s.tryAdvance(seen::add);
        boolean r3 = s.tryAdvance(seen::add);
        if (!r1 || !r2 || !r3) {
            throw new RuntimeException("Expected true for available elements");
        }
        if (!seen.equals(listOf("x", "y", "z"))) {
            throw new RuntimeException("Unexpected elements or order: " + seen);
        }
    }

    private static void testTryAdvanceReturnsFalseWhenExhausted() {
        Spliterator<String> s = newSpliterator(listOf("only"));
        List<String> seen = new ArrayList<>();
        if (!s.tryAdvance(seen::add)) {
            throw new RuntimeException("Expected true for the first element");
        }
        if (s.tryAdvance(seen::add)) {
            throw new RuntimeException("Expected false after exhaustion");
        }
        if (seen.size() != 1) {
            throw new RuntimeException("Action invoked unexpected number of times: " + seen.size());
        }
    }

    private static void testEmptyIteratorReturnsFalseWithoutInvokingAction() {
        Spliterator<String> s = newSpliterator(new ArrayList<String>());
        List<String> seen = new ArrayList<>();
        if (s.tryAdvance(seen::add)) {
            throw new RuntimeException("Expected false for empty iterator");
        }
        if (!seen.isEmpty()) {
            throw new RuntimeException("Action must not be invoked for empty iterator");
        }
    }

    private static List<String> listOf(String... values) {
        List<String> list = new ArrayList<>();
        for (String v : values) {
            list.add(v);
        }
        return list;
    }
}
