/*
 * @test
 * @summary Test for computeIfAbsent method in ImmutableCollections.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.Function;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentThrowsUOE();
    }

    public static void testComputeIfAbsentThrowsUOE() {
        try {
            // Instantiate an anonymous subclass to access the method
            class TestMap extends java.util.ImmutableCollections.AbstractImmutableMap {
                @Override
                public Object computeIfAbsent(Object key, Function<?, ?> mf) {
                    return java.util.ImmutableCollections.computeIfAbsent(key, (Function)mf);
                }
            }
            TestMap map = new TestMap();
            // Call computeIfAbsent with dummy key and function
            map.computeIfAbsent("testKey", new Function<Object, Object>() {
                @Override
                public Object apply(Object t) {
                    return null;
                }
            });
            // If no exception, fail the test
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("PASS: computeIfAbsent throws UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
