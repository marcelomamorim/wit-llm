/*
 * @test
 * @summary Tests for ConcurrentHashMap.removeIf method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithNonNullPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.removeIf(null);
            // Expecting NullPointerException
            System.out.println("FAIL: Expected NullPointerException for null predicate");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null predicate");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testRemoveIfWithNonNullPredicate() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Predicate<String> predicate = v -> v.equals("value1");
        boolean result = map.removeIf(predicate);
        if (result && !map.containsKey("key1") && map.containsKey("key2")) {
            System.out.println("PASS: removeIf removed matching entries");
        } else {
            System.out.println("FAIL: removeIf did not behave as expected");
        }
    }
}
