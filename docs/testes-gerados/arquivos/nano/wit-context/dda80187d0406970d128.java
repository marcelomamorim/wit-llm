/*
 * @test
 * @summary Tests for java.util.IdentityHashMap.replaceAll method.
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.function.BiFunction;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullMap();
    }

    static void testReplaceAllWithNullFunction() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("key1", "value1");
        try {
            map.replaceAll(null);
            System.out.println("FAIL: Expected NullPointerException for null function");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null function");
        }
    }

    static void testReplaceAllWithValidFunction() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        BiFunction<String, String, String> func = (k, v) -> v + v;
        map.replaceAll(func);
        boolean testPassed = "11".equals(map.get("a")) && "22".equals(map.get("b"));
        System.out.println(testPassed ? "PASS" : "FAIL") + ": replaceAll applied correctly";
    }

    static void testReplaceAllWithNullMap() {
        IdentityHashMap<String, String> map = null;
        try {
            IdentityHashMap<String, String> nullMap = map;
            nullMap.replaceAll((k, v) -> v);
            System.out.println("FAIL: Expected NullPointerException for null map");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null map");
        }
    }
}
