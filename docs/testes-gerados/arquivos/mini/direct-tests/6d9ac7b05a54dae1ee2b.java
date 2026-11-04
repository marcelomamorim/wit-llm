/*
 * @test
 * @summary Test that computeIfPresent throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
            @Override
            public String computeIfPresent(String key, BiFunction<? super String, ? super String, ? extends String> rf) {
                throw ImmutableCollections.uoe();
            }
        };

        boolean thrown = false;
        try {
            map.computeIfPresent("key", new BiFunction<String, String, String>() {
                @Override
                public String apply(String k, String v) {
                    return "value";
                }
            });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException");
        }
    }
}
