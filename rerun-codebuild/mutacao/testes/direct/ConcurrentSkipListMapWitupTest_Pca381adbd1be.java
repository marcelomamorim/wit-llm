/*
 * @test    @summary Test replaceAll method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_Pca381adbd1be
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest_Pca381adbd1be {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithNullValues();
    }

    static void testReplaceAllWithValidFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
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
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            map.replaceAll(null);
            assert false : "Expected NullPointerException";
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
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
