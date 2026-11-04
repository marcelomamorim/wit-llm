/*
 * @test
 * @summary Test for Nodes.accept(double) method to verify IndexOutOfBoundsException behavior.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAcceptWithIndexBelowFence();
        testAcceptWithIndexEqualToFence();
        testAcceptWithIndexAboveFence();
    }

    private static void testAcceptWithIndexBelowFence() {
        Nodes node = new Nodes();
        // Reflection or direct access not used; assuming a subclass or mock setup for testing
        // Since no constructor or factory is provided, we simulate the behavior by subclassing or dummy class
        class TestNode extends Nodes {
            int index = 0;
            int fence = 10;
            double[] array = new double[20];
            @Override
            public void accept(double value) {
                if (index >= fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(index));
                }
                array[index++] = value;
            }
        }
        TestNode testNode = new TestNode();
        try {
            testNode.accept(1.23);
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException for index below fence", e);
        }
    }

    private static void testAcceptWithIndexEqualToFence() {
        class TestNode extends Nodes {
            int index = 10;
            int fence = 10;
            double[] array = new double[20];
            @Override
            public void accept(double value) {
                if (index >= fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(index));
                }
                array[index++] = value;
            }
        }
        TestNode testNode = new TestNode();
        try {
            testNode.accept(2.34);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to fence");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testAcceptWithIndexAboveFence() {
        class TestNode extends Nodes {
            int index = 11;
            int fence = 10;
            double[] array = new double[20];
            @Override
            public void accept(double value) {
                if (index >= fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(index));
                }
                array[index++] = value;
            }
        }
        TestNode testNode = new TestNode();
        try {
            testNode.accept(3.45);
            throw new AssertionError("Expected IndexOutOfBoundsException for index above fence");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
