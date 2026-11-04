/* @test    @summary Test WeakHashMap toArray method for correct array conversion and deep copy behavior    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.Arrays;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testToArrayWithEmptyMap();
        testToArrayWithSingleEntry();
        testToArrayWithMultipleEntries();
        testToArrayNullArray();
    }

    static void testToArrayWithEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String[] result = map.toArray(new String[0]);
        assert result != null : "Result should not be null";
        assert result.length == 0 : "Array length should be zero";
    }

    static void testToArrayWithSingleEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        String[] result = map.toArray(new String[1]);
        assert result != null : "Result should not be null";
        assert result.length >= 1 : "Array length should be at least 1";
        boolean foundKey = false;
        boolean foundValue = false;
        for (String s : result) {
            if ("key1".equals(s)) {
                foundKey = true;
            }
            if ("value1".equals(s)) {
                foundValue = true;
            }
        }
        assert foundKey : "Array should contain key1";
        assert foundValue : "Array should contain value1";
    }

    static void testToArrayWithMultipleEntries() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        String[] result = map.toArray(new String[2]);
        assert result != null : "Result should not be null";
        assert result.length >= 2 : "Array length should be at least 2";
        boolean foundKey1 = false;
        boolean foundKey2 = false;
        boolean foundValue1 = false;
        boolean foundValue2 = false;
        for (String s : result) {
            if ("key1".equals(s)) {
                foundKey1 = true;
            }
            if ("key2".equals(s)) {
                foundKey2 = true;
            }
            if ("value1".equals(s)) {
                foundValue1 = true;
            }
            if ("value2".equals(s)) {
                foundValue2 = true;
            }
        }
        assert foundKey1 : "Array should contain key1";
        assert foundKey2 : "Array should contain key2";
        assert foundValue1 : "Array should contain value1";
        assert foundValue2 : "Array should contain value2";
    }

    static void testToArrayNullArray() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");
        try {
            map.toArray(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
