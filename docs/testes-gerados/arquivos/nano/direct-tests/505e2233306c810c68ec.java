/* @test    @summary Test for ImmutableCollections.next() method behavior    @run main ImmutableCollectionsWitupTest */

import java.util.Map;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testNextReturnsEntryWhenHasNext();
        testNextThrowsWhenNoNext();
    }

    static void testNextReturnsEntryWhenHasNext() {
        // Setup a minimal mock or subclass to simulate the behavior
        class TestImmutableCollections extends java.util.ImmutableCollections {
            private int callCount = 0;
            private final Map.Entry<K, V> entry;

            TestImmutableCollections(Map.Entry<K, V> entry) {
                this.entry = entry;
            }

            boolean hasNext() {
                return callCount == 0;
            }

            int nextIndex() {
                callCount++;
                return 0;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (hasNext()) {
                    while (table[nextIndex()] == null) {}
                    @SuppressWarnings("unchecked")
                    Map.Entry<K, V> e = new KeyValueHolder<>(entry.getKey(), entry.getValue());
                    remaining--;
                    return e;
                } else {
                    throw new NoSuchElementException();
                }
            }
        }
        // Create a dummy KeyValueHolder class for test
        class KeyValueHolder<K, V> implements Map.Entry<K, V> {
            private final K key;
            private final V value;
            KeyValueHolder(K key, V value) {
                this.key = key;
                this.value = value;
            }
            public K getKey() { return key; }
            public V getValue() { return value; }
            public V setValue(V v) { throw new UnsupportedOperationException(); }
        }
        Map.Entry<String, Integer> testEntry = new KeyValueHolder<>("key", 42);
        TestImmutableCollections testInstance = new TestImmutableCollections(testEntry);
        Map.Entry<String, Integer> result = testInstance.next();
        assert result.getKey().equals("key") : "Expected key 'key'";
        assert result.getValue() == 42 : "Expected value 42";
    }

    static void testNextThrowsWhenNoNext() {
        class TestImmutableCollections extends java.util.ImmutableCollections {
            boolean hasNext() { return false; }
            int nextIndex() { return 0; }
            @Override
            public Map.Entry<K, V> next() {
                if (hasNext()) {
                    while (table[nextIndex()] == null) {}
                    @SuppressWarnings("unchecked")
                    Map.Entry<K, V> e = new KeyValueHolder<>(null, null);
                    remaining--;
                    return e;
                } else {
                    throw new NoSuchElementException();
                }
            }
        }
        class KeyValueHolder<K, V> implements Map.Entry<K, V> {
            private final K key;
            private final V value;
            KeyValueHolder(K key, V value) {
                this.key = key;
                this.value = value;
            }
            public K getKey() { return key; }
            public V getValue() { return value; }
            public V setValue(V v) { throw new UnsupportedOperationException(); }
        }
        TestImmutableCollections testInstance = new TestImmutableCollections();
        try {
            testInstance.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
