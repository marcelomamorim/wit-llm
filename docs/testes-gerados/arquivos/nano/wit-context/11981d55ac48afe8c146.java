/*
 * @test
 * @summary Tests for java.util.stream.Nodes class, specifically the asArray method.
 * @run main NodesWitupTest
 */

import java.util.function.IntFunction;
import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsArrayReturnsArrayWhenSizeMatches();
        testAsArrayThrowsWhenSizeMismatch();
    }

    private static void testAsArrayReturnsArrayWhenSizeMatches() {
        // Setup: create a dummy array and generator
        Integer[] array = {1, 2, 3};
        IntFunction<Integer[]> generator = size -> new Integer[size];
        // Call: should return the same array if array.length == curSize
        Integer[] result = Nodes.asArray(generator);
        // Verify: result is the same as array
        if (result != array) {
            throw new AssertionError("Expected same array to be returned");
        }
    }

    private static void testAsArrayThrowsWhenSizeMismatch() {
        // Setup: create a dummy array with size 2
        Integer[] array = {1, 2};
        // Override the array in Nodes class for testing purposes
        // Since array is static final, we cannot modify it directly; instead, we test the method with a mock scenario
        // but as per constraints, we test the actual method behavior with a mismatched array
        // We simulate the mismatch by calling asArray with a generator that produces a different size
        IntFunction<Integer[]> generator = size -> new Integer[size + 1];
        try {
            // This will call asArray, which checks array.length == curSize
            // Since array.length is 2, and generator produces size+1, the method should throw
            Nodes.asArray(generator);
            throw new AssertionError("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
