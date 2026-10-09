/* @test    @summary Test ConcurrentSkipListMap.remove(Object, Object) method for correctness and exception handling.    @run main ConcurrentSkipListMapWitupTest_P008a84872b6f */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P008a84872b6f {

    public static void main(String[] args) {
        testRemoveWithExistingKeyAndValue();
        testRemoveWithExistingKeyAndNullValue();
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveWithNonExistingKey();
        testRemoveWithIncorrectValue();
    }

    static void testRemoveWithExistingKeyAndValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean result = map.remove("key1", "value1");
        assert result : "Expected removal to succeed with existing key and matching value";
        assert !map.containsKey("key1") : "Map should not contain key after removal";
    }

    static void testRemoveWithExistingKeyAndNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key2", null);
        boolean result = false;
        try {
            result = map.remove("key2", null);
        } catch (NullPointerException e) {
            assert false : "remove should handle null value if key exists";
        }
        assert result : "Expected removal to succeed with existing key and null value";
        assert !map.containsKey("key2") : "Map should not contain key after removal";
    }

    static void testRemoveWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            thrown = true;
        }
        assert thrown : "remove should throw NullPointerException for null key";
    }

    static void testRemoveWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key3", "value3");
        boolean result = false;
        try {
            result = map.remove("key3", null);
        } catch (NullPointerException e) {
            assert false : "remove should handle null value if key exists";
        }
        assert !result : "Expected removal to fail with existing key and null value";
        assert map.containsKey("key3") : "Map should still contain key after failed removal";
    }

    static void testRemoveWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.remove("nonexistent", "value");
        assert !result : "Removal should fail for non-existing key";
    }

    static void testRemoveWithIncorrectValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key4", "value4");
        boolean result = map.remove("key4", "differentValue");
        assert !result : "Removal should fail with incorrect value";
        assert map.containsKey("key4") : "Map should still contain key after failed removal";
    }
}
