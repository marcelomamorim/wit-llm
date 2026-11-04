/*
 * @test
 * @summary Test ImmutableCollections.next() method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    static class TestIterator<K, V> {
        final Object[] table;
        int idx = 0;
        int remaining;

        TestIterator(Object[] table, int remaining) {
            this.table = table;
            this.remaining = remaining;
        }

        boolean hasNext() {
            return remaining > 0;
        }

        int nextIndex() {
            // advance idx by 2 until a non-null element is found
            while (idx < table.length && table[idx] == null) {
                idx += 2;
            }
            return idx;
        }

        public Map.Entry<K, V> next() {
            if (hasNext()) {
                while (table[nextIndex()] == null) {}
                @SuppressWarnings("unchecked")
                Map.Entry<K, V> e = new KeyValueHolder<>((K) table[idx], (V) table[idx + 1]);
                remaining--;
                idx += 2;
                return e;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    static class KeyValueHolder<K, V> implements Map.Entry<K, V> {
        private final K key;
        private final V value;

        KeyValueHolder(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        testNextReturnsEntriesInOrder();
        testNextThrowsWhenNoMoreElements();
    }

    private static void testNextReturnsEntriesInOrder() {
        Object[] table = new Object[] {"key1", "value1", null, null, "key2", "value2"};
        TestIterator<String, String> it = new TestIterator<>(table, 2);

        Map.Entry<String, String> e1 = it.next();
        if (!"key1".equals(e1.getKey()) || !"value1".equals(e1.getValue())) {
            throw new AssertionError("First entry mismatch");
        }

        Map.Entry<String, String> e2 = it.next();
        if (!"key2".equals(e2.getKey()) || !"value2".equals(e2.getValue())) {
            throw new AssertionError("Second entry mismatch");
        }
    }

    private static void testNextThrowsWhenNoMoreElements() {
        Object[] table = new Object[] {"key1", "value1"};
        TestIterator<String, String> it = new TestIterator<>(table, 1);

        it.next(); // consume the only element

        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
