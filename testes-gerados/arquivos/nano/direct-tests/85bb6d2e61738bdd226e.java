/*
 * @test
 * @summary Test the next() method of LinkedHashMap for correct behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectEntry();
        testNextOnEmptyMapReturnsNull();
    }

    static void testNextReturnsCorrectEntry() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        var iterator = map.entrySet().iterator();
        Map.Entry<String, Integer> entry1 = iterator.next();
        Map.Entry<String, Integer> entry2 = iterator.next();
        Map.Entry<String, Integer> entry3 = iterator.next();
        assert entry1.getKey().equals("a") : "First entry key should be 'a'";
        assert entry2.getKey().equals("b") : "Second entry key should be 'b'";
        assert entry3.getKey().equals("c") : "Third entry key should be 'c'";
    }

    static void testNextOnEmptyMapReturnsNull() {
        LinkedHashMap<String, String> emptyMap = new LinkedHashMap<>();
        var iterator = emptyMap.entrySet().iterator();
        boolean hasNext = iterator.hasNext();
        assert !hasNext : "Iterator on empty map should have no next";
        try {
            iterator.next();
            assert false : "Calling next() on empty iterator should throw NoSuchElementException";
        } catch (java.util.NoSuchElementException e) {
            // expected
        }
    }
}
