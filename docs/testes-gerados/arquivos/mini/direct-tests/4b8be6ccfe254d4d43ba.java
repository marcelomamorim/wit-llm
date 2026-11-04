/*
 * @test
 * @summary Test TreeMap.next() method returns nextEntry correctly
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        // Test next() returns entries in ascending key order
        Map.Entry<Integer, String> e1 = it.next();
        if (e1.getKey() != 1 || !"one".equals(e1.getValue())) {
            throw new RuntimeException("First entry incorrect: " + e1);
        }

        Map.Entry<Integer, String> e2 = it.next();
        if (e2.getKey() != 2 || !"two".equals(e2.getValue())) {
            throw new RuntimeException("Second entry incorrect: " + e2);
        }

        Map.Entry<Integer, String> e3 = it.next();
        if (e3.getKey() != 3 || !"three".equals(e3.getValue())) {
            throw new RuntimeException("Third entry incorrect: " + e3);
        }

        // Confirm no more elements
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have no more elements");
        }
    }
}
