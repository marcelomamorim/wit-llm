/*
 * @test    @summary Test replaceAll method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_Pdeb1f54aa761
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest_Pdeb1f54aa761 {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithNullValues();
        System.out.println("All tests passed.");
    }

    static void testReplaceAllWithValidFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        
        BiFunction<Integer, String, String> func = (k, v) -> v.toUpperCase();
        map.replaceAll(func);
        if (!"ONE".equals(map.get(1)) || !"TWO".equals(map.get(2)) || !"THREE".equals(map.get(3))) {
            throw new AssertionError("replaceAll did not update values correctly.");
        }
    }

    static void testReplaceAllWithNullFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllWithNullValues() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        BiFunction<Integer, String, String> func = (k, v) -> null;
        try {
            map.replaceAll(func);
            throw new AssertionError("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
