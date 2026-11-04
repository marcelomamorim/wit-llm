/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.putAll throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testPutAllThrowsUOE();
    }

    static void testPutAllThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
        };

        Map<String, String> toPut = new HashMap<>();
        toPut.put("key", "value");

        try {
            map.putAll(toPut);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
