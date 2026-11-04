/*
 * @test
 * @summary Test ImmutableCollections.MapN.MapNIterator.next() for NoSuchElementException and normal iteration
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    static class DummyMapNIterator<K, V> {
        private final Object[] table;
        private int idx = 0;
        private int remaining;

        DummyMapNIterator(Object[] table) {
            this.table = table;
            this.remaining = table.length / 2;
        }

        private int nextIndex() {
            int current = idx;
            idx += 2;
            return current;
        }

        public boolean hasNext() {
            return remaining > 0;
        }

        public Map.Entry<K, V> next() {
            if (hasNext()) {
                while (table[nextIndex()] == null) {}
                @SuppressWarnings("unchecked")
                Map.Entry<K, V> e = new KeyValueHolder<>((K) table[idx - 2], (V) table[idx - 1]);
                remaining--;
                return e;
            } else {
                throw new NoSuchElementException();
            }
        }

        // Simple Map.Entry implementation for testing
        static class KeyValueHolder<K, V> implements Map.Entry<K, V> {
            private final K key;
            private final V value;

            KeyValueHolder(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() { return key; }
            public V getValue() { return value; }
            public V setValue(V value) { throw new UnsupportedOperationException(); }
        }
    }

    public static void main(String[] args) {
        testNextNormalIteration();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextNormalIteration() {
        Object[] table = new Object[] {"key1", "value1", "key2", "value2"};
        DummyMapNIterator<String, String> it = new DummyMapNIterator<>(table);

        Map.Entry<String, String> e1 = it.next();
        if (!"key1".equals(e1.getKey()) || !"value1".equals(e1.getValue())) {
            throw new AssertionError("First entry mismatch");
        }

        Map.Entry<String, String> e2 = it.next();
        if (!"key2".equals(e2.getKey()) || !"value2".equals(e2.getValue())) {
            throw new AssertionError("Second entry mismatch");
        }

        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more elements");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        Object[] table = new Object[] {"key1", "value1"};
        DummyMapNIterator<String, String> it = new DummyMapNIterator<>(table);

        it.next(); // consume the only element

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException not thrown");
        }
    }
}
