/*
 * @test
 * @summary Test ConcurrentHashMap.keySet(V) for null argument
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testKeySetWithNull();
        testKeySetWithValidValue();
    }

    static void testKeySetWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.keySet(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    static void testKeySetWithValidValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String value = "mapped";
        try {
            Object result = map.keySet(value);
            if (result != null) {
                System.out.println("Passed: keySet returned non-null for valid value");
            } else {
                System.err.println("Failed: keySet returned null for valid value");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
