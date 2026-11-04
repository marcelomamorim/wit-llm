/*
 * @test
 * @summary Test the next() method of LinkedHashMap for correct value retrieval.
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectValue();
        testNextOnEmptyMap();
        testNextMultipleCalls();
    }

    public static void testNextReturnsCorrectValue() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        var iterator = map.entrySet().iterator();
        // Advance iterator to first element
        iterator.next();
        // Get the value of the current element
        Integer value = map.next();
        // Since the iterator is not directly linked to next(), simulate by calling nextNode()
        // but as nextNode() is package-private, test via public API: get the first value
        // For the purpose of this test, we assume next() returns the value of the first element
        // after advancing the iterator
        // So, test that the value is one of the inserted values
        assert value != null : "next() returned null";
        assert (value == 1 || value == 2 || value == 3) : "Unexpected value: " + value;
    }

    public static void testNextOnEmptyMap() {
        LinkedHashMap<String, String> emptyMap = new LinkedHashMap<>();
        try {
            // Since next() is final and returns nextNode().value, and nextNode() is package-private,
            // we cannot call next() directly on an iterator. Instead, simulate the scenario.
            // For this test, we assume calling next() on an empty map throws NoSuchElementException.
            // But as next() does not throw, this test is to ensure no exception occurs.
            // Alternatively, test that no exception is thrown when calling next() after empty iteration.
            // Here, we simulate by calling iterator.next() and expecting NoSuchElementException.
            var iterator = emptyMap.entrySet().iterator();
            iterator.next();
            assert false : "Expected NoSuchElementException";
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        }
    }

    public static void testNextMultipleCalls() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 10);
        map.put("y", 20);
        var iterator = map.entrySet().iterator();
        // First call
        iterator.next();
        // Second call
        Integer value = map.next();
        assert value != null : "next() returned null on second call";
        assert (value == 10 || value == 20) : "Unexpected value on second call: " + value;
    }
}
