/*
 * @test
 * @summary Test that ImmutableCollections.putAll always throws UnsupportedOperationException
 * @run main ImmutableCollectionsPutAllTest
 */

import java.util.*;

public class ImmutableCollectionsPutAllTest {

    public static void main(String[] args) {
        testPutAllThrows();
    }

    static void testPutAllThrows() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            ImmutableCollections.putAll(map);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected exception
        }
    }
}
