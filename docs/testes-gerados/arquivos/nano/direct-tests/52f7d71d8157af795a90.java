/*
 * @test
 * @summary Test for Collections.putAll to ensure it throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutAllThrows();
    }

    public static void testPutAllThrows() {
        Map<String, String> map = new HashMap<>();
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("key1", "value1");
        sourceMap.put("key2", "value2");
        try {
            Collections.putAll(map, sourceMap);
            // If no exception, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
