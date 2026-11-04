/*
 * @test
 * @summary Behavioral tests for WeakHashMap values spliterator tryAdvance
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    static final List<Object> STRONG_KEYS = new ArrayList<>();

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testDrainsAllValues();
        testNullValueIsDelivered();
        testConcurrentModificationDetected();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        String k = new String("a");
        STRONG_KEYS.add(k);
        m.put(k, "1");
        Spliterator<String> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance((java.util.function.Consumer<? super String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testEmptyMapReturnsFalse() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        Spliterator<String> s = m.values().spliterator();
        boolean[] called = new boolean[1];
        boolean result = s.tryAdvance(v -> called[0] = true);
        check(!result, "empty map tryAdvance must return false");
        check(!called[0], "action must not be invoked for empty map");
    }

    static void testDrainsAllValues() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        String k1 = new String("k1");
        String k2 = new String("k2");
        String k3 = new String("k3");
        STRONG_KEYS.add(k1);
        STRONG_KEYS.add(k2);
        STRONG_KEYS.add(k3);
        m.put(k1, "v1");
        m.put(k2, "v2");
        m.put(k3, "v3");

        Spliterator<String> s = m.values().spliterator();
        List<String> got = new ArrayList<>();
        while (s.tryAdvance(got::add)) {
            // drain
        }
        check(got.size() == 3, "expected 3 values, got " + got.size());
        check(got.contains("v1"), "missing v1");
        check(got.contains("v2"), "missing v2");
        check(got.contains("v3"), "missing v3");
        check(!s.tryAdvance(v -> { }), "tryAdvance must return false after exhaustion");
    }

    static void testNullValueIsDelivered() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        String k = new String("nk");
        STRONG_KEYS.add(k);
        m.put(k, null);
        Spliterator<String> s = m.values().spliterator();
        String[] box = new String[1];
        boolean result = s.tryAdvance(v -> box[0] = v);
        check(result, "entry with null value must still be advanced");
        check(box[0] == null, "null value must be delivered to action");
        check(!s.tryAdvance(v -> { }), "no more elements expected");
    }

    static void testConcurrentModificationDetected() {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        String k = new String("a");
        STRONG_KEYS.add(k);
        m.put(k, "1");
        Spliterator<String> s = m.values().spliterator();
        String newKey = new String("z");
        STRONG_KEYS.add(newKey);
        boolean thrown = false;
        try {
            s.tryAdvance(v -> m.put(newKey, "x"));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during action must raise ConcurrentModificationException");
    }
}
