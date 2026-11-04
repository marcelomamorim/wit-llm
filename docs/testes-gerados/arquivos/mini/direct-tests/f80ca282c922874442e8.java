/*
 * @test
 * @summary Test WeakHashMap next() method returns nextEntry() correctly
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.Iterator;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();
        map.put(key1, "value1");
        map.put(key2, "value2");

        Iterator<Map.Entry<Object, String>> it = map.entrySet().iterator();

        // Call next() and check it returns a Map.Entry with expected key and value
        Map.Entry<Object, String> e1 = it.next();
        if (e1 == null || e1.getKey() == null || e1.getValue() == null) {
            throw new RuntimeException("next() returned null or entry with null key/value");
        }

        Map.Entry<Object, String> e2 = it.next();
        if (e2 == null || e2.getKey() == null || e2.getValue() == null) {
            throw new RuntimeException("second next() returned null or entry with null key/value");
        }

        // There should be exactly two entries
        if (it.hasNext()) {
            throw new RuntimeException("Iterator has more elements than expected");
        }

        // Check keys and values are as expected
        boolean foundKey1 = (e1.getKey() == key1 && "value1".equals(e1.getValue())) || (e2.getKey() == key1 && "value1".equals(e2.getValue()));
        boolean foundKey2 = (e1.getKey() == key2 && "value2".equals(e1.getValue())) || (e2.getKey() == key2 && "value2".equals(e2.getValue()));

        if (!foundKey1 || !foundKey2) {
            throw new RuntimeException("Entries returned by next() do not match inserted keys and values");
        }
    }
}
