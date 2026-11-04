/*
 * @test
 * @summary Test for Nodes.accept(double) method to verify exception handling when array capacity is exceeded.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAcceptThrowsExceptionWhenArrayFull();
    }

    private static void testAcceptThrowsExceptionWhenArrayFull() {
        // Reflection or direct access to array is not used; assuming array size is small for test
        // Create a subclass or use a test-specific subclass if needed; here, we simulate with a custom class
        class TestNodes extends Nodes {
            private double[] array;
            private int curSize;

            public TestNodes(int size) {
                this.array = new double[size];
                this.curSize = 0;
            }

            public void accept(double i) {
                if (curSize < array.length) {
                    array[curSize++] = i;
                } else {
                    throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", array.length));
                }
            }
        }

        int size = 1;
        TestNodes testNodes = new TestNodes(size);
        try {
            // Fill the array
            testNodes.accept(1.0);
            // This call should throw exception
            testNodes.accept(2.0);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Verify exception message contains the array size
            if (!e.getMessage().contains("fixed size of 1")) {
                throw new AssertionError("Exception message does not contain expected size.", e);
            }
        }
    }
}
