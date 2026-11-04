/*
 * @test
 * @summary Test Nodes.begin() method for correct behavior and exception throwing
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testBeginWithMatchingSize();
        testBeginWithNonMatchingSize();
    }

    static void testBeginWithMatchingSize() {
        // Prepare a dummy array with a specific size
        int[] array = new int[10];
        // Create an instance of Nodes class (assuming it has a default constructor or static context)
        // Since Nodes is a final class with only static methods, we simulate the call via reflection
        try {
            java.lang.reflect.Method beginMethod = Nodes.class.getDeclaredMethod("begin", long.class);
            // Create a dummy object to invoke the static method
            beginMethod.setAccessible(true);
            // Call with size matching array length
            beginMethod.invoke(null, (long) array.length);
            System.out.println("testBeginWithMatchingSize passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testBeginWithMatchingSize", e);
        }
    }

    static void testBeginWithNonMatchingSize() {
        int[] array = new int[10];
        try {
            java.lang.reflect.Method beginMethod = Nodes.class.getDeclaredMethod("begin", long.class);
            beginMethod.setAccessible(true);
            // Call with size not matching array length to trigger exception
            beginMethod.invoke(null, (long) (array.length + 1));
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalStateException)) {
                throw new AssertionError("Expected IllegalStateException, but got: " + cause);
            }
            System.out.println("testBeginWithNonMatchingSize passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testBeginWithNonMatchingSize", e);
        }
    }
}
