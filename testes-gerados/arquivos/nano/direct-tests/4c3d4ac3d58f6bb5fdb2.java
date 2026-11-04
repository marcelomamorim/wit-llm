/* @test    @summary Test HashMap.next() method for correct iteration behavior    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.Map;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectEntry();
        testNextOnEmptyMap();
        testNextMultipleCalls();
    }

    static void testNextReturnsCorrectEntry() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        java.util.Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        boolean foundA = false, foundB = false, foundC = false;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getKey().equals("a") && entry.getValue() == 1) {
                foundA = true;
            } else if (entry.getKey().equals("b") && entry.getValue() == 2) {
                foundB = true;
            } else if (entry.getKey().equals("c") && entry.getValue() == 3) {
                foundC = true;
            }
        }
        assert foundA : "Entry with key 'a' not found or incorrect";
        assert foundB : "Entry with key 'b' not found or incorrect";
        assert foundC : "Entry with key 'c' not found or incorrect";
    }

    static void testNextOnEmptyMap() {
        HashMap<String, String> emptyMap = new HashMap<>();
        java.util.Iterator<Map.Entry<String, String>> iterator = emptyMap.entrySet().iterator();
        assert !iterator.hasNext() : "Iterator should have no next on empty map";
    }

    static void testNextMultipleCalls() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<Integer, String> firstEntry = iterator.next();
            assert firstEntry != null : "First entry should not be null";
        }
        if (iterator.hasNext()) {
            Map.Entry<Integer, String> secondEntry = iterator.next();
            assert secondEntry != null : "Second entry should not be null";
        }
        // No assertion for third as map has only two entries
    }
}
