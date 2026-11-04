/*
 * @test    @summary Test for ConcurrentHashMap.contains(Object o) method.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithNonNull();
    }

    private static void testContainsWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = false;
        try {
            result = map.contains(null);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught as expected when calling contains(null)");
            return;
        }
        if (result) {
            System.out.println("Test passed: contains(null) returned true");
        } else {
            System.out.println("Test passed: contains(null) returned false");
        }
    }

    private static void testContainsWithNonNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.contains("value1");
        if (result) {
            System.out.println("Test passed: contains(" + "value1") + ") returned true");
        } else {
            System.out.println("Test failed: contains(" + "value1") + ") returned false");
        }
        boolean result2 = map.contains("value2");
        if (!result2) {
            System.out.println("Test passed: contains(" + "value2") + ") returned false");
        } else {
            System.out.println("Test failed: contains(" + "value2") + ") returned true");
        }
    }
}
