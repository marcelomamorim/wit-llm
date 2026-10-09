/*
 * @test    @summary Test replace(K key, V oldValue, V newValue) method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest_Pe3825a11589c
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pe3825a11589c {

    public static void main(String[] args) {
        testReplaceMethod();
    }

    private static void testReplaceMethod() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "testKey";
        String initialValue = "initial";
        String oldValue = "old";
        String newValue = "new";

        // Test replacing a non-existing key should return false
        boolean result1 = map.replace(key, oldValue, newValue);
        if (result1) {
            throw new AssertionError("Expected false when replacing non-existing key") ;
        }

        // Insert key with initial value
        map.put(key, initialValue);

        // Attempt to replace with incorrect oldValue should return false
        boolean result2 = map.replace(key, oldValue, newValue);
        if (result2) {
            throw new AssertionError("Expected false when oldValue does not match") ;
        }

        // Correct oldValue, should return true and replace value
        boolean result3 = map.replace(key, initialValue, newValue);
        if (!result3) {
            throw new AssertionError("Expected true when replacing with correct oldValue") ;
        }

        // Verify that the value was replaced
        String currentValue = map.get(key);
        if (!newValue.equals(currentValue)) {
            throw new AssertionError("Value was not replaced correctly") ;
        }

        // Additional check: replacing with null should throw NullPointerException
        try {
            map.replace(key, null, "anotherValue");
            throw new AssertionError("Expected NullPointerException when newValue is null") ;
        } catch (NullPointerException e) {
            // expected
        }

        try {
            map.replace(null, initialValue, "anotherValue");
            throw new AssertionError("Expected NullPointerException when key is null") ;
        } catch (NullPointerException e) {
            // expected
        }

        try {
            map.replace(key, initialValue, null);
            throw new AssertionError("Expected NullPointerException when newValue is null") ;
        } catch (NullPointerException e) {
            // expected
        }
    }
}
