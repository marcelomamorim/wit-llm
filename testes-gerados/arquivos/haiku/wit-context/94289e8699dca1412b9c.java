/*
 * @test
 * @summary WeakHashMap values spliterator tryAdvance: null action, empty map, value delivery and modification during action
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testDeliversAllValuesThenFalse();
        testModificationDuringActionThrowsCME();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        List<String> keys = new ArrayList<>();
        String k = new String("a");
        keys.add(k);
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        m.put(k, 1);
        Spliterator<Integer> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
        check(keys.size() == 1, "keys must stay strongly reachable");
    }

    static void testEmptyMapReturnsFalse() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        Spliterator<Integer> s = m.values().spliterator();
        boolean advanced = s.tryAdvance(v -> {
            throw new RuntimeException("action must not be called on empty map");
        });
        check(!advanced, "tryAdvance on empty map must return false");
    }

    static void testDeliversAllValuesThenFalse() {
        List<String> keys = new ArrayList<>();
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        for (int i = 1; i <= 3; i++) {
            String k = "key" + i;
            keys.add(k);
            m.put(k, i);
        }
        Spliterator<Integer> s = m.values().spliterator();
        List<Integer> seen = new ArrayList<>();
        int calls = 0;
        while (s.tryAdvance(seen::add)) {
            calls++;
            check(calls <= 3, "tryAdvance returned true more times than entries");
        }
        check(seen.size() == 3, "expected 3 values, got " + seen.size());
        int sum = 0;
        for (Integer v : seen) {
            sum += v;
        }
        check(sum == 6, "expected value sum 6, got " + sum);
        check(!s.tryAdvance(v -> { throw new RuntimeException("no more elements"); }),
              "tryAdvance after exhaustion must return false");
        check(keys.size() == 3, "keys must stay strongly reachable");
    }

    static void testModificationDuringActionThrowsCME() {
        List<String> keys = new ArrayList<>();
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        for (int i = 1; i <= 2; i++) {
            String k = "k" + i;
            keys.add(k);
            m.put(k, i);
        }
        Spliterator<Integer> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(v -> m.put("extra", 99));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during action must throw ConcurrentModificationException");
        check(keys.size() == 2, "keys must stay strongly reachable");
    }
}
