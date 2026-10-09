/* @test    @summary Test for ConcurrentHashMap.forEach with null BiConsumer    @run main ConcurrentHashMapWitupTest_P9e56e26ddea2 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_P9e56e26ddea2 {

    public static void main(String[] args) {
        testForEachNullAction();
    }

    private static void testForEachNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
