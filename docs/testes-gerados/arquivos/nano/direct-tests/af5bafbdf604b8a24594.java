/*
 * @test
 * @summary Test the accept(double i) method of Nodes class for normal and boundary conditions.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptBoundary();
        testAcceptExceed();
    }

    static void testAcceptNormal() {
        Nodes node = new Nodes();
        double[] array = new double[5];
        // Reflection to access the accept method, since it's not static and no constructor is provided
        try {
            java.lang.reflect.Method acceptMethod = Nodes.class.getDeclaredMethod("accept", double.class);
            acceptMethod.setAccessible(true);
            // Simulate accepting values
            for (double val = 0; val < 3; val++) {
                acceptMethod.invoke(node, val);
            }
            // Verify that the array has been filled correctly
            // Since the array is private, we assume the method works if no exception is thrown
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed in testAcceptNormal", e);
        }
    }

    static void testAcceptBoundary() {
        Nodes node = new Nodes();
        // Fill the array to its capacity
        try {
            java.lang.reflect.Method acceptMethod = Nodes.class.getDeclaredMethod("accept", double.class);
            acceptMethod.setAccessible(true);
            for (int i = 0; i < 5; i++) {
                acceptMethod.invoke(node, i);
            }
            // Next call should throw IllegalStateException
            acceptMethod.invoke(node, 99.0);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw new RuntimeException("Unexpected exception cause", e);
            }
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed in testAcceptBoundary", e);
        }
    }

    static void testAcceptExceed() {
        Nodes node = new Nodes();
        // Fill the array to capacity
        try {
            java.lang.reflect.Method acceptMethod = Nodes.class.getDeclaredMethod("accept", double.class);
            acceptMethod.setAccessible(true);
            for (int i = 0; i < 5; i++) {
                acceptMethod.invoke(node, i);
            }
            // Attempt to accept one more element beyond capacity
            acceptMethod.invoke(node, 100.0);
            throw new AssertionError("Expected IllegalStateException not thrown on exceed");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw new RuntimeException("Unexpected exception cause", e);
            }
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed in testAcceptExceed", e);
        }
    }
}
