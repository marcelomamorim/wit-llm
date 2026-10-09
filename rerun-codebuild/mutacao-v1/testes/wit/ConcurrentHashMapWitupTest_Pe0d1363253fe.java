/* @test    @summary Test for ConcurrentHashMap.containsValue(Object) with null input to verify NullPointerException is thrown    @run main ConcurrentHashMapWitupTest_Pe0d1363253fe */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pe0d1363253fe {

    public static void main(String[] args) {
        testContainsValueNull();
    }

    public static void testContainsValueNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.containsValue(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Test passes, exception was thrown as expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
