/*
 * @test
 * @summary Checks public array-returning views of WeakHashMap that delegate to toArray(T[]) behavior, including oversized arrays, empty maps and null values
 * @run main WeakHashMapWitupTest
 */

import java.lang.ref.Reference;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testKeySetToArrayExactSize();
        testValuesToArrayOversizedArrayIsReturnedAndTerminated();
        testToArrayEmptyMap();
        testToArrayWithNullValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testKeySetToArrayExactSize() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        String k1 = new String("a");
        String k2 = new String("b");
        map.put(k1, 1);
        map.put(k2, 2);

        String[] arr = map.keySet().toArray(new String[0]);
        check(arr.length == 2, "expected length 2 but was " + arr.length);

        Set<String> found = new HashSet<>(Arrays.asList(arr));
        check(found.contains("a"), "missing key a");
        check(found.contains("b"), "missing key b");

        Reference.reachabilityFence(k1);
        Reference.reachabilityFence(k2);
    }

    static void testValuesToArrayOversizedArrayIsReturnedAndTerminated() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        String k1 = new String("x");
        String k2 = new String("y");
        map.put(k1, 10);
        map.put(k2, 20);

        Integer[] big = new Integer[5];
        Arrays.fill(big, Integer.valueOf(-1));
        Integer[] result = map.values().toArray(big);

        check(result == big, "array large enough should be returned directly");
        check(big[2] == null, "element after last value should be null");

        Set<Integer> found = new HashSet<>(Arrays.asList(result).subList(0, 2));
        check(found.contains(10), "missing value 10");
        check(found.contains(20), "missing value 20");

        Reference.reachabilityFence(k1);
        Reference.reachabilityFence(k2);
    }

    static void testToArrayEmptyMap() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        Integer[] arr = map.values().toArray(new Integer[0]);
        check(arr.length == 0, "expected empty array but was length " + arr.length);

        String[] keys = map.keySet().toArray(new String[0]);
        check(keys.length == 0, "expected empty key array");
    }

    static void testToArrayWithNullValue() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        String k = new String("k");
        map.put(k, null);

        Integer[] arr = map.values().toArray(new Integer[0]);
        check(arr.length == 1, "expected length 1 but was " + arr.length);
        check(arr[0] == null, "expected null value element");

        Reference.reachabilityFence(k);
    }
}
