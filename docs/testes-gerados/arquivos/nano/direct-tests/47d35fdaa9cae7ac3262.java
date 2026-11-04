/*
 * @test
 * @summary Test Nodes.begin() method for correct behavior and exception throwing
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testBeginWithMatchingSize();
        testBeginWithMismatchedSize();
    }

    static void testBeginWithMatchingSize() {
        // Prepare a dummy array with a specific size
        int[] array = new int[10];
        // Create a Nodes instance with the array
        Nodes nodes = new Nodes(array);
        // Call begin() with size equal to array length
        try {
            nodes.begin(array.length);
            System.out.println("testBeginWithMatchingSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithMatchingSize failed: " + e);
        }
    }

    static void testBeginWithMismatchedSize() {
        int[] array = new int[10];
        Nodes nodes = new Nodes(array);
        // Call begin() with size different from array length
        try {
            nodes.begin(array.length + 1);
            System.err.println("testBeginWithMismatchedSize failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testBeginWithMismatchedSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithMismatchedSize failed: " + e);
        }
    }
}
