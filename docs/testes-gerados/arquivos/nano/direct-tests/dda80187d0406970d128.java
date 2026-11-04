/*
 * @test
 * @summary Tests the replaceAll method of IdentityHashMap for correct behavior.
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.function.BiFunction;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithIdentityFunction();
        testReplaceAllWithCustomFunction();
    }

    static void testReplaceAllWithNullFunction() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, 1);
        map.put(key2, 2);
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testReplaceAllWithIdentityFunction() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, 10);
        map.put(key2, 20);
        BiFunction<String, Integer, Integer> identityFunction = (k, v) -> v;
        map.replaceAll(identityFunction);
        if (map.get(key1) != 10 || map.get(key2) != 20) {
            throw new AssertionError("replaceAll with identity function altered values");
        }
    }

    static void testReplaceAllWithCustomFunction() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, 5);
        map.put(key2, 15);
        BiFunction<String, Integer, Integer> addTenFunction = (k, v) -> v + 10;
        map.replaceAll(addTenFunction);
        if (map.get(key1) != 15 || map.get(key2) != 25) {
            throw new AssertionError("replaceAll with addTenFunction did not update values correctly");
        }
    }
}
