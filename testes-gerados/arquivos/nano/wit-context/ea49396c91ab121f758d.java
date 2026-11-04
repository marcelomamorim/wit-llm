/*
 * @test
 * @summary Test for ConcurrentHashMap.removeIf with null predicate
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.removeIf(null);
            System.out.println("Test failed: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
