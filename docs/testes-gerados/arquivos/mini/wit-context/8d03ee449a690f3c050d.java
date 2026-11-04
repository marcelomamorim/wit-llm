/*
 * @test
 * @summary Test ConcurrentHashMap.ValuesView.addAll throws UnsupportedOperationException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.ValuesView;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        // Create a ConcurrentHashMap and get its ValuesView
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ValuesView<String, String> valuesView = map.values();

        // Prepare a dummy collection to pass to addAll
        Collection<String> dummyCollection = java.util.Arrays.asList("a", "b");

        // Test that addAll throws UnsupportedOperationException
        try {
            valuesView.addAll(dummyCollection);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        System.out.println("Test passed: addAll throws UnsupportedOperationException as expected.");
    }
}
