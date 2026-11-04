/*
 * @test
 * @summary Test TreeMap.next() method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Iterator<Integer> it = map.keySet().iterator();

        // Test next() returns keys in ascending order
        Integer first = it.next();
        if (!first.equals(10)) {
            throw new RuntimeException("Expected first key 10 but got " + first);
        }

        Integer second = it.next();
        if (!second.equals(20)) {
            throw new RuntimeException("Expected second key 20 but got " + second);
        }

        Integer third = it.next();
        if (!third.equals(30)) {
            throw new RuntimeException("Expected third key 30 but got " + third);
        }

        // After all elements, next() should throw NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected NoSuchElementException after last element");
        }
    }
}
