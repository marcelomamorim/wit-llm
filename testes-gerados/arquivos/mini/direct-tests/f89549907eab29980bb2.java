/*
 * @test
 * @summary Basic tests for ConcurrentSkipListMap.put(K,V) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test 1: put a new key-value pair, expect null return
        String prev = map.put(1, "one");
        if (prev != null) {
            throw new AssertionError("Expected null when inserting new key, got: " + prev);
        }

        // Test 2: put a value for an existing key, expect old value returned
        prev = map.put(1, "uno");
        if (!"one".equals(prev)) {
            throw new AssertionError("Expected old value 'one' when replacing key 1, got: " + prev);
        }

        // Test 3: put multiple keys and verify values
        map.put(2, "two");
        map.put(3, "three");
        if (!"two".equals(map.get(2))) {
            throw new AssertionError("Expected value 'two' for key 2");
        }
        if (!"three".equals(map.get(3))) {
            throw new AssertionError("Expected value 'three' for key 3");
        }

        // Test 4: put null key should throw NullPointerException
        try {
            map.put(null, "null");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 5: put null value is allowed (ConcurrentSkipListMap permits null values?)
        // Actually ConcurrentSkipListMap does NOT permit null values, so expect NullPointerException
        try {
            map.put(4, null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
