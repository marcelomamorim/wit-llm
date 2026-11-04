/*
 * @test
 * @summary Test for ImmutableCollections.computeIfPresent method
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.*;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfPresentThrowsUOE();
    }

    public static void testComputeIfPresentThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public Set<Map.Entry<String, String>> entrySet() {
                return Collections.emptySet();
            }
        };
        BiFunction<String, String, String> rf = (k, v) -> v;
        try {
            map.computeIfPresent("key", rf);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
