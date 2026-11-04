/*
 * @test
 * @summary Test HashMap.next() method behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsCorrectValue();
        testNextOnEmptyMapThrows();
    }

    private static void testNextReturnsCorrectValue() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<String> it = map.values().iterator();

        // next() should return one of the values inserted
        String val1 = it.next();
        if (!"value1".equals(val1) && !"value2".equals(val1)) {
            throw new RuntimeException("Unexpected value returned by next(): " + val1);
        }

        // next() again should return the other value
        String val2 = it.next();
        if (!"value1".equals(val2) && !"value2".equals(val2)) {
            throw new RuntimeException("Unexpected value returned by next(): " + val2);
        }

        if (val1.equals(val2)) {
            throw new RuntimeException("next() returned the same value twice");
        }
    }

    private static void testNextOnEmptyMapThrows() {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> it = map.values().iterator();

        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
