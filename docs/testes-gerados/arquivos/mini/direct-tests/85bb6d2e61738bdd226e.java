/*
 * @test
 * @summary Test LinkedHashMap.next() returns entries in insertion order
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();

        Map.Entry<String, Integer> e1 = it.next();
        if (!"one".equals(e1.getKey()) || e1.getValue() != 1) {
            throw new AssertionError("First entry incorrect: " + e1);
        }

        Map.Entry<String, Integer> e2 = it.next();
        if (!"two".equals(e2.getKey()) || e2.getValue() != 2) {
            throw new AssertionError("Second entry incorrect: " + e2);
        }

        Map.Entry<String, Integer> e3 = it.next();
        if (!"three".equals(e3.getKey()) || e3.getValue() != 3) {
            throw new AssertionError("Third entry incorrect: " + e3);
        }

        if (it.hasNext()) {
            throw new AssertionError("Iterator has unexpected extra elements");
        }
    }
}
