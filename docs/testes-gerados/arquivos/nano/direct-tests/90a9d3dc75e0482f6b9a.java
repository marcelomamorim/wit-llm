/*
 * @test
 * @summary Test the next() method of ConcurrentSkipListMap for correct behavior.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsExpectedValue();
        testNextOnEmptyMap();
    }

    private static void testNextReturnsExpectedValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        // Obtain an iterator over the map's entries
        var iterator = map.entrySet().iterator();
        // Move to the first element
        if (iterator.hasNext()) {
            var entry = iterator.next();
            // Access the key and value
            int key = entry.getKey();
            String value = entry.getValue();
            // Validate that the value matches expected for the key
            if (key == 1 && "one".equals(value)) {
                System.out.println("testNextReturnsExpectedValue passed.");
            } else {
                throw new AssertionError("Unexpected entry: key=" + key + ", value=" + value);
            }
        } else {
            throw new AssertionError("Iterator has no elements.");
        }
    }

    private static void testNextOnEmptyMap() {
        ConcurrentSkipListMap<Integer, String> emptyMap = new ConcurrentSkipListMap<>();
        var iterator = emptyMap.entrySet().iterator();
        if (!iterator.hasNext()) {
            System.out.println("testNextOnEmptyMap passed.");
        } else {
            throw new AssertionError("Iterator should have no elements.");
        }
    }
}
