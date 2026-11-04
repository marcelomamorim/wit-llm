/*
 * @test
 * @summary Test WeakHashMap next() method returns correct values
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.Map;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsValue();
    }

    private static void testNextReturnsValue() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        // Collect values returned by next() to verify correctness
        boolean foundValue1 = false;
        boolean foundValue2 = false;

        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String val = entry.getValue();
            if ("value1".equals(val)) {
                foundValue1 = true;
            } else if ("value2".equals(val)) {
                foundValue2 = true;
            } else {
                throw new RuntimeException("Unexpected value returned by next(): " + val);
            }
        }

        if (!foundValue1 || !foundValue2) {
            throw new RuntimeException("next() did not return all expected values");
        }
    }
}
