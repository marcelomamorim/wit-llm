/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap values spliterator tryAdvance
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyMapThrowsNPE();
        testEmptyMapReturnsFalse();
        testAdvanceInAscendingKeyOrder();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        Spliterator<String> s = map.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) should throw NullPointerException");
    }

    static void testNullActionOnEmptyMapThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Spliterator<String> s = map.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) on empty map should throw NullPointerException");
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Spliterator<String> s = map.values().spliterator();
        List<String> seen = new ArrayList<>();
        boolean advanced = s.tryAdvance(seen::add);
        check(!advanced, "tryAdvance on empty map should return false");
        check(seen.isEmpty(), "no action should be applied on empty map");
    }

    static void testAdvanceInAscendingKeyOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");
        Collection<String> values = map.values();
        Spliterator<String> s = values.spliterator();
        List<String> seen = new ArrayList<>();
        while (s.tryAdvance(seen::add)) {
            // consume all elements
        }
        check(seen.size() == 3, "expected 3 values but got " + seen.size());
        check(seen.get(0).equals("a"), "first value should be a");
        check(seen.get(1).equals("b"), "second value should be b");
        check(seen.get(2).equals("c"), "third value should be c");
        check(!s.tryAdvance(seen::add), "tryAdvance after exhaustion should return false");
        check(seen.size() == 3, "no extra action after exhaustion");
    }
}
