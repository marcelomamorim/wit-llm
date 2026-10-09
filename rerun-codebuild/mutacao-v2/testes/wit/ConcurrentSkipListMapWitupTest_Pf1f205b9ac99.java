/*
 * @test
 * @summary Test ConcurrentSkipListMap.putIfAbsent behavior including null checks
 * @run main ConcurrentSkipListMapWitupTest_Pf1f205b9ac99
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pf1f205b9ac99 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test that putIfAbsent throws NullPointerException if value is null
        try {
            map.putIfAbsent("key", null);
            throw new Error("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }

        // Test that putIfAbsent throws NullPointerException if key is null
        // but only if value is non-null (here "value")
        try {
            map.putIfAbsent(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test normal putIfAbsent behavior: absent key inserts value
        String prev = map.putIfAbsent("a", "1");
        if (prev != null) {
            throw new Error("Expected null return for absent key");
        }
        if (!"1".equals(map.get("a"))) {
            throw new Error("Value not inserted correctly");
        }

        // Test putIfAbsent does not overwrite existing value
        prev = map.putIfAbsent("a", "2");
        if (!"1".equals(prev)) {
            throw new Error("Expected previous value '1' to be returned");
        }
        if (!"1".equals(map.get("a"))) {
            throw new Error("Value should not be overwritten by putIfAbsent");
        }
    }
}
