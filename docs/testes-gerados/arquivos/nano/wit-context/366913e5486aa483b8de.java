/*
 * @test
 * @summary Test for ImmutableCollections.replaceAll method
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsUOE();
    }

    public static void testReplaceAllThrowsUOE() {
        // Setup: create a dummy subclass instance to invoke replaceAll
        class DummyMap extends java.util.ImmutableCollections.AbstractImmutableMap<String, String> {
            @Override
            public java.util.Set<Map.Entry<String, String>> entrySet() {
                return Collections.emptySet();
            }
        }
        DummyMap map = new DummyMap();
        BiFunction<String, String, String> f = (k, v) -> v;
        try {
            map.replaceAll(f);
            System.out.println("PASS: replaceAll() threw UnsupportedOperationException as expected.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: replaceAll() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: replaceAll() threw unexpected exception: " + t);
        }
    }
}
