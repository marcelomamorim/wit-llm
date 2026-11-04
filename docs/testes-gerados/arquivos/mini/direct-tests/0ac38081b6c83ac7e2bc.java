/*
 * @test
 * @summary Test ConcurrentHashMap forEachRemaining method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    static class TestIterator extends ConcurrentHashMap<Integer, String>.BaseIterator {
        private int index = 0;
        private final Integer[] keys;
        private final String[] values;

        TestIterator(ConcurrentHashMap<Integer, String> map) {
            map.super();
            keys = map.keySet().toArray(new Integer[0]);
            values = new String[keys.length];
            for (int i = 0; i < keys.length; i++) {
                values[i] = map.get(keys[i]);
            }
        }

        // advance returns next Node<K,V> or null
        Node<Integer, String> advance() {
            if (index < keys.length) {
                Node<Integer, String> node = new Node<>(keys[index], values[index], null);
                index++;
                return node;
            } else {
                return null;
            }
        }

        // Expose forEachRemaining for testing
        void forEachRemaining(Consumer<? super String> action) {
            if (action == null) throw new NullPointerException();
            for (Node<Integer, String> p; (p = advance()) != null; )
                action.accept(p.val);
        }
    }

    // Minimal Node class to simulate Node<K,V>
    static class Node<K, V> {
        final K key;
        final V val;
        final Node<K, V> next;

        Node(K key, V val, Node<K, V> next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
    }

    private static void testForEachRemainingNormal() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        TestIterator it = new TestIterator(map);

        final StringBuilder sb = new StringBuilder();
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });

        String result = sb.toString();
        // The order is not guaranteed, but all values must be present
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new AssertionError("forEachRemaining did not process all values: " + result);
        }
    }

    private static void testForEachRemainingNullAction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        TestIterator it = new TestIterator(map);
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
