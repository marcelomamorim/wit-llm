/* @test    @summary Test computeIfAbsent for ConcurrentHashMap with null key or mappingFunction    @run main ConcurrentHashMapWitupTest_P95e254341436 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P95e254341436 {

    public static void main(String[] args) {
        testComputeIfAbsentNullKey();
        testComputeIfAbsentNullFunction();
    }

    private static void testComputeIfAbsentNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.computeIfAbsent(null, k -> "value");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    private static void testComputeIfAbsentNullFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.computeIfAbsent("key", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null mappingFunction");
        }
    }
}
