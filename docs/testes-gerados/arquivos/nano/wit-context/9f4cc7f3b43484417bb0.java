/* @test    @summary Test for WeakHashMap.toArray(T[] a) method behavior.    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.Arrays;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testToArrayWithEmptyMap();
        testToArrayWithSingleEntry();
        testToArrayWithMultipleEntries();
    }

    static void testToArrayWithEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String[] array = new String[0];
        String[] result = map.toArray(array);
        if (result != array) {
            throw new AssertionError("Expected returned array to be the same as input array.");
        }
        if (result.length != 0) {
            throw new AssertionError("Expected array length to be 0.");
        }
    }

    static void testToArrayWithSingleEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        String[] array = new String[1];
        String[] result = map.toArray(array);
        if (result != array) {
            throw new AssertionError("Expected returned array to be the same as input array.");
        }
        if (result.length != 1) {
            throw new AssertionError("Expected array length to be 1.");
        }
        if (!Arrays.asList(result).contains("key1") && !Arrays.asList(result).contains("value1")) {
            throw new AssertionError("Array does not contain expected entries.");
        }
    }

    static void testToArrayWithMultipleEntries() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        String[] array = new String[2];
        String[] result = map.toArray(array);
        if (result != array) {
            throw new AssertionError("Expected returned array to be the same as input array.");
        }
        if (result.length != 2) {
            throw new AssertionError("Expected array length to be 2.");
        }
        if (!(Arrays.asList(result).contains("key1") && Arrays.asList(result).contains("key2"))) {
            throw new AssertionError("Array does not contain expected keys.");
        }
        if (!(Arrays.asList(result).contains("value1") && Arrays.asList(result).contains("value2"))) {
            throw new AssertionError("Array does not contain expected values.");
        }
    }
}
