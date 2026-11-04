/* @test    @summary Test HashMap forEach method for correct iteration and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.BiConsumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithNullAction();
        testConcurrentModificationException();
    }

    static void testForEachWithEntries() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        map.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String key, Integer value) {
                result.append(key).append(value);
            }
        });
        boolean hasA = result.toString().contains("a1");
        boolean hasB = result.toString().contains("b2");
        boolean hasC = result.toString().contains("c3");
        if (!(hasA && hasB && hasC)) {
            throw new AssertionError("forEach did not process all entries correctly") ;
        }
    }

    static void testForEachWithNullAction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("x", "y");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown") ;
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConcurrentModificationException() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.forEach(new BiConsumer<Integer, String>() {
                @Override
                public void accept(Integer key, String value) {
                    map.put(3, "three");
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException not thrown") ;
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        }
    }
}
