/*
 * @test
 * @summary Behavioral tests for WeakHashMap keySet spliterator tryAdvance
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMapReturnsFalse() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Spliterator<String> sp = map.keySet().spliterator();
        check(!sp.tryAdvance(k -> { throw new AssertionError("unexpected"); }),
              "tryAdvance on empty map must return false");
    }

    static void testNullActionThrowsNPE() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(new String("a"), "1");
        Spliterator<String> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testIteratesAllKeys() {
        List<String> held = new ArrayList<>();
        held.add(new String("a"));
        held.add(new String("b"));
        held.add(new String("c"));
        WeakHashMap<String, String> map = new WeakHashMap<>();
        for (String k : held) {
            map.put(k, "v");
        }
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        while (sp.tryAdvance(seen::add)) {
            // drain
        }
        check(seen.size() == 3, "expected 3 keys, got " + seen.size());
        for (String k : held) {
            check(seen.contains(k), "missing key " + k);
        }
        check(!sp.tryAdvance(seen::add), "exhausted spliterator must return false");
        check(seen.size() == 3, "no extra element must be delivered");
    }

    static void testNullKeyIsDelivered() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(null, "v");
        Spliterator<String> sp = map.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        boolean advanced = sp.tryAdvance(seen::add);
        check(advanced, "tryAdvance must return true when a key exists");
        check(seen.size() == 1 && seen.get(0) == null, "null key must be delivered as null");
        check(!sp.tryAdvance(seen::add), "no further elements expected");
    }

    static void testModificationDuringActionThrowsCME() {
        List<String> held = new ArrayList<>();
        held.add(new String("x"));
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(held.get(0), "1");
        Set<String> keys = map.keySet();
        Spliterator<String> sp = keys.spliterator();
        String extra = new String("y");
        held.add(extra);
        boolean thrown = false;
        try {
            sp.tryAdvance(k -> map.put(extra, "2"));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during action must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testEmptyMapReturnsFalse();
        testNullActionThrowsNPE();
        testIteratesAllKeys();
        testNullKeyIsDelivered();
        testModificationDuringActionThrowsCME();
    }
}
