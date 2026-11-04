/*
 * @test
 * @summary WITUP-derived checks for ConcurrentSkipListMap value spliterator forEachRemaining (null action and ordered traversal)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionLeavesSpliteratorUsable();
        testEmptyMapNoActions();
        testValuesInKeyOrder();
        testFullTraversalOnce();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");
        return map;
    }

    static void testNullActionThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        Spliterator<String> sp = map.values().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testNullActionLeavesSpliteratorUsable() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        Spliterator<String> sp = map.values().spliterator();
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            // expected
        }
        List<String> seen = new ArrayList<>();
        Consumer<String> collector = seen::add;
        sp.forEachRemaining(collector);
        check(seen.size() == 3, "spliterator should still yield all values after null action");
        check(seen.get(0).equals("a"), "first value after failed call should be a");
        check(seen.get(1).equals("b"), "second value after failed call should be b");
        check(seen.get(2).equals("c"), "third value after failed call should be c");
    }

    static void testEmptyMapNoActions() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Spliterator<String> sp = map.values().spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty map should produce no actions");
    }

    static void testValuesInKeyOrder() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        Spliterator<String> sp = map.values().spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.size() == 3, "expected three values");
        check(seen.get(0).equals("a"), "values should follow ascending key order: first");
        check(seen.get(1).equals("b"), "values should follow ascending key order: second");
        check(seen.get(2).equals("c"), "values should follow ascending key order: third");
    }

    static void testFullTraversalOnce() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        Spliterator<String> sp = map.values().spliterator();
        List<String> first = new ArrayList<>();
        sp.forEachRemaining(first::add);
        List<String> second = new ArrayList<>();
        sp.forEachRemaining(second::add);
        check(first.size() == 3, "first traversal should visit all values");
        check(second.isEmpty(), "second traversal on same spliterator should visit nothing");
    }
}
