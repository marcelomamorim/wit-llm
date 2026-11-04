/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.replace throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of AbstractImmutableMap to test replace
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
            @Override
            public java.util.Set<Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
        };

        boolean uoeThrown = false;
        try {
            map.replace("key", "oldValue", "newValue");
        } catch (UnsupportedOperationException e) {
            uoeThrown = true;
        }

        if (!uoeThrown) {
            throw new AssertionError("Expected UnsupportedOperationException from replace method");
        }

        System.out.println("Test passed: replace() throws UnsupportedOperationException as expected.");
    }
}
