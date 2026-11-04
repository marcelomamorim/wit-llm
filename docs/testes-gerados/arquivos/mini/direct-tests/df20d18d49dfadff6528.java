/*
 * @test
 * @summary Test that computeIfAbsent throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.Function;
import java.util.AbstractMap;
import java.util.Map;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ImmutableCollections.AbstractImmutableMap to test computeIfAbsent
        Map<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
        };

        boolean uoeThrown = false;
        try {
            map.computeIfAbsent("key", new Function<String, String>() {
                public String apply(String s) {
                    return "value";
                }
            });
        } catch (UnsupportedOperationException e) {
            uoeThrown = true;
        }

        if (!uoeThrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}
