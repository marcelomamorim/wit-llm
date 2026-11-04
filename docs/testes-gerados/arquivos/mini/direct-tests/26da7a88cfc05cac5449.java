/*
 * @test
 * @summary Test TreeMap.next() method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsValuesInOrder();
    }

    private static void testNextReturnsValuesInOrder() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        // The next() method of the iterator returns Map.Entry, so we test TreeMap's nextEntry indirectly
        // by iterating and checking values returned by next().value

        Map.Entry<Integer, String> e1 = it.next();
        if (!"ten".equals(e1.getValue())) {
            throw new RuntimeException("Expected value 'ten' but got: " + e1.getValue());
        }

        Map.Entry<Integer, String> e2 = it.next();
        if (!"twenty".equals(e2.getValue())) {
            throw new RuntimeException("Expected value 'twenty' but got: " + e2.getValue());
        }

        Map.Entry<Integer, String> e3 = it.next();
        if (!"thirty".equals(e3.getValue())) {
            throw new RuntimeException("Expected value 'thirty' but got: " + e3.getValue());
        }

        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have no more elements");
        }
    }
}
