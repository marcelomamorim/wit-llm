/*
 * @test
 * @summary Witup-derived checks for WeakHashMap keySet spliterator tryAdvance
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testIteratesAllKeysThenReturnsFalse();
        testConcurrentModificationFromAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        List<String> keys = new ArrayList<>();
        keys.add(new String("a"));
        map.put(keys.get(0), 1);
        Spliterator<String> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testIteratesAllKeysThenReturnsFalse() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        List<String> keys = new ArrayList<>();
        keys.add(new String("k1"));
        keys.add(new String("k2"));
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), i);
        }
        map.put(null, 99);

        Spliterator<String> sp = map.keySet().spliterator();
        Set<String> seen = new HashSet<>();
        Consumer<String> collector = seen::add;
        int count = 0;
        while (sp.tryAdvance(collector)) {
            count++;
        }
        check(count == 3, "expected 3 elements but was " + count);
        check(seen.size() == 3, "expected 3 distinct keys");
        check(seen.contains(null), "null key must be visited");
        check(seen.contains(keys.get(0)), "k1 must be visited");
        check(seen.contains(keys.get(1)), "k2 must be visited");
        check(!sp.tryAdvance(collector), "exhausted spliterator must return false");
        check(keys.size() == 2, "strong key references must remain alive");
    }

    static void testConcurrentModificationFromAction() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        List<String> keys = new ArrayList<>();
        keys.add(new String("only"));
        map.put(keys.get(0), 1);
        List<String> added = new ArrayList<>();
        Spliterator<String> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(k -> {
                String extra = new String("extra");
                added.add(extra);
                map.put(extra, 2);
            });
        } catch (ConcurrentModificationException expected) {
            thrown = true;
        }
        check(thrown, "structural modification in action must cause ConcurrentModificationException");
    }
}
