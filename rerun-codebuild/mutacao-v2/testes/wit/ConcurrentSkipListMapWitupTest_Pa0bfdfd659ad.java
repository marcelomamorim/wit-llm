/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsValue method for null and present/absent values
 * @run main ConcurrentSkipListMapWitupTest_Pa0bfdfd659ad
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pa0bfdfd659ad {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test that containsValue throws NullPointerException for null argument
        boolean npeThrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null value argument");
        }

        // Test containsValue returns false on empty map
        if (map.containsValue("any")) {
            throw new AssertionError("containsValue should return false on empty map");
        }

        // Put some entries
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Test containsValue returns true for existing values
        if (!map.containsValue("value1")) {
            throw new AssertionError("containsValue should return true for existing value 'value1'");
        }
        if (!map.containsValue("value2")) {
            throw new AssertionError("containsValue should return true for existing value 'value2'");
        }

        // Test containsValue returns false for non-existing value
        if (map.containsValue("value3")) {
            throw new AssertionError("containsValue should return false for non-existing value 'value3'");
        }

        // Test containsValue returns false for null values stored (should not be possible, but test anyway)
        // ConcurrentSkipListMap does not allow null values, so no need to test this scenario

        System.out.println("Test passed.");
    }
}
