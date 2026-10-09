/* @test    @summary Test HashMap.replaceAll method for correct value transformation    @run main HashMapWitupTest_P4637f9ea2617 */

import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest_P4637f9ea2617 {

    public static void main(String[] args) {
        testReplaceAllWithIncrement();
        testReplaceAllWithNullFunction();
        testReplaceAllEmptyMap();
    }

    static void testReplaceAllWithIncrement() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);
        BiFunction<Integer, Integer, Integer> incrementFunction = (k, v) -> v + 1;
        map.replaceAll(incrementFunction);
        assert map.get(1) == 11 : "Expected 11, got " + map.get(1);
        assert map.get(2) == 21 : "Expected 21, got " + map.get(2);
        assert map.get(3) == 31 : "Expected 31, got " + map.get(3);
    }

    static void testReplaceAllWithNullFunction() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 10);
        try {
            map.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testReplaceAllEmptyMap() {
        HashMap<Integer, String> emptyMap = new HashMap<>();
        BiFunction<Integer, String, String> concatFunction = (k, v) -> v + "_test";
        emptyMap.replaceAll(concatFunction);
        assert emptyMap.size() == 0 : "Empty map should remain empty";
    }
}
