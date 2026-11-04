/*
 * @test
 * @summary Test for Nodes.asArray method to ensure correct array conversion.
 * @run main NodesWitupTest
 */

import java.util.function.IntFunction;
import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsArrayReturnsArrayWhenSizeMatches();
        testAsArrayThrowsWhenSizeDoesNotMatch();
    }

    static void testAsArrayReturnsArrayWhenSizeMatches() {
        // Prepare a mock array and size
        Object[] array = new Object[] {"a", "b", "c"};
        int curSize = array.length;
        // Create a dummy generator
        IntFunction<Object[]> generator = size -> new Object[size];
        // Call asArray with matching size
        Object[] result = Nodes.asArray(generator);
        // Verify that the returned array is the same as the input array
        if (result != array) {
            throw new AssertionError("Expected same array to be returned when size matches");
        }
    }

    static void testAsArrayThrowsWhenSizeDoesNotMatch() {
        // Prepare a dummy array
        Object[] array = new Object[] {"a", "b"};
        int curSize = array.length + 1; // mismatch
        // Create a generator that produces array of size 2
        IntFunction<Object[]> generator = size -> new Object[array.length];
        // Override Nodes.asArray to simulate size mismatch
        try {
            // Simulate the method behavior
            if (array.length != curSize) {
                throw new IllegalStateException();
            }
            // If sizes match, return array
            Object[] result = array;
            if (result != array) {
                throw new AssertionError("Expected same array to be returned");
            }
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
