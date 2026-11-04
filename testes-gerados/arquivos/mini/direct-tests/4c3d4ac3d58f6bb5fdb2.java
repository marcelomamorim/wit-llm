/*
 * @test
 * @summary Test HashMap.next() method returns nextNode entry correctly
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapWitupTest {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        // next() should return the next entry
        Map.Entry<String, String> e1 = it.next();
        if (e1 == null || e1.getKey() == null || e1.getValue() == null) {
            throw new RuntimeException("Iterator.next() returned null or entry with null key/value");
        }

        Map.Entry<String, String> e2 = it.next();
        if (e2 == null || e2.getKey() == null || e2.getValue() == null) {
            throw new RuntimeException("Iterator.next() returned null or entry with null key/value");
        }

        // There should be exactly two entries
        if (it.hasNext()) {
            throw new RuntimeException("Iterator has more elements than expected");
        }
    }
}
