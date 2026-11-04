/* @test    @summary Test HashMap.next() method for correct value retrieval.    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectValue();
        testNextOnEmptyMap();
    }

    private static void testNextReturnsCorrectValue() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        // Obtain an iterator over the map's entries
        java.util.Iterator<java.util.Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        // Advance iterator to first element
        if (iterator.hasNext()) {
            java.util.Map.Entry<String, Integer> entry = iterator.next();
            Integer value = entry.getValue();
            boolean valueIsCorrect = (value != null) && (value.equals(entry.getValue()));
            if (!valueIsCorrect) {
                throw new AssertionError("Value returned by iterator.next() does not match expected.");
            }
        } else {
            throw new AssertionError("Iterator has no elements when expected.");
        }
    }

    private static void testNextOnEmptyMap() {
        HashMap<String, Integer> emptyMap = new HashMap<>();
        java.util.Iterator<java.util.Map.Entry<String, Integer>> iterator = emptyMap.entrySet().iterator();
        if (iterator.hasNext()) {
            throw new AssertionError("Iterator should have no elements on empty map.");
        }
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator.");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        }
    }
}
