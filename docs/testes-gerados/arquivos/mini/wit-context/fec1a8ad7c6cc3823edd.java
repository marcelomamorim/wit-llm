/*
 * @test
 * @summary Test Collections.EmptyMap.putIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testEmptyMapPutIfAbsent();
    }

    private static void testEmptyMapPutIfAbsent() {
        java.util.Map<String, String> emptyMap = Collections.emptyMap();
        try {
            emptyMap.putIfAbsent("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
