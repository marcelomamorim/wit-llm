/*
 * @test
 * @summary Checks public Spliterator.tryAdvance behavior of ArrayList spliterators: null action, empty list, single advance and fail-fast on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyListReturnsFalse();
        testAdvancesOneElement();
        testConcurrentModificationDetected();
    }

    static void fail(String message) {
        throw new AssertionError(message);
    }

    static void testNullActionThrowsNPE() {
        List<String> list = new ArrayList<>();
        list.add("a");
        Spliterator<String> sp = list.spliterator();
        try {
            sp.tryAdvance(null);
            fail("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyListReturnsFalse() {
        List<String> list = new ArrayList<>();
        Spliterator<String> sp = list.spliterator();
        boolean advanced = sp.tryAdvance(x -> fail("action must not be called on empty list"));
        if (advanced) {
            fail("tryAdvance on empty list must return false");
        }
    }

    static void testAdvancesOneElement() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> seen = new ArrayList<>();
        Spliterator<String> sp = list.spliterator();
        boolean advanced = sp.tryAdvance(seen::add);
        if (!advanced) {
            fail("tryAdvance must return true when elements remain");
        }
        if (seen.size() != 1 || !seen.get(0).equals("a")) {
            fail("expected exactly first element to be consumed, got " + seen);
        }
    }

    static void testConcurrentModificationDetected() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Spliterator<String> sp = list.spliterator();
        try {
            sp.tryAdvance(x -> list.add("c"));
            fail("expected ConcurrentModificationException after structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
