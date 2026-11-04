/* @test    @summary Test TreeMap.replaceAll method for correct behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.function.BiFunction;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithConcurrentModification();
    }

    static void testReplaceAllWithValidFunction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        BiFunction<Integer, String, String> func = (k, v) -> v.toUpperCase();
        map.replaceAll(func);

        assert "ONE".equals(map.get(1)) : "Expected 'ONE' for key 1";
        assert "TWO".equals(map.get(2)) : "Expected 'TWO' for key 2";
        assert "THREE".equals(map.get(3)) : "Expected 'THREE' for key 3";
    }

    static void testReplaceAllWithNullFunction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        try {
            map.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllWithConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        BiFunction<Integer, String, String> func = (k, v) -> {
            if (k == 2) {
                map.put(4, "four"); // structural modification during iteration
                return v;
            }
            return v.toUpperCase();
        };

        try {
            map.replaceAll(func);
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
