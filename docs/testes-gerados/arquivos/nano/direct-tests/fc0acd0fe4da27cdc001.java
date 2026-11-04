/* @test    @summary Test WeakHashMap.next() method for correct key retrieval.    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectKey();
        testNextOnEmptyMapReturnsNull();
    }

    private static void testNextReturnsCorrectKey() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        String key = "testKey";
        map.put(key, 42);
        WeakHashMap<String, Integer>.EntryIterator iterator = map.new EntryIterator();
        String resultKey = iterator.next();
        assert resultKey.equals(key) : "Expected key to be 'testKey', but got: " + resultKey;
    }

    private static void testNextOnEmptyMapReturnsNull() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        WeakHashMap<String, Integer>.EntryIterator iterator = map.new EntryIterator();
        String resultKey = iterator.next();
        assert resultKey == null : "Expected null when iterating empty map, but got: " + resultKey;
    }
}
