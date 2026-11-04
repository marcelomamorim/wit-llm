/* @test    @summary Test for WeakHashMap.next() method behavior    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.Map;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsEntry();
        testNextOnEmptyMap();
    }

    private static void testNextReturnsEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        WeakHashMap.Entry<String, String> entry = map.next();
        assert entry != null : "next() should return a non-null entry when map has entries";
        assert entry.getKey() != null : "Entry key should not be null";
        assert entry.getValue() != null : "Entry value should not be null";
    }

    private static void testNextOnEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        WeakHashMap.Entry<String, String> entry = map.next();
        assert entry == null : "next() should return null when map is empty";
    }
}
