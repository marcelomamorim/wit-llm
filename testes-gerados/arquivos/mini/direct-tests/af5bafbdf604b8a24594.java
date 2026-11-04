/*
 * @test
 * @summary Test java.util.stream.Nodes.accept(double) method behavior
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class TestNode {
        final double[] array;
        int curSize = 0;

        TestNode(int size) {
            this.array = new double[size];
        }

        public void accept(double i) {
            if (curSize < array.length) {
                array[curSize++] = i;
            } else {
                throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", array.length));
            }
        }
    }

    public static void main(String[] args) {
        testAcceptWithinBounds();
        testAcceptExceedBounds();
    }

    private static void testAcceptWithinBounds() {
        TestNode node = new TestNode(3);
        node.accept(1.1);
        node.accept(2.2);
        node.accept(3.3);

        if (node.curSize != 3) {
            throw new AssertionError("curSize should be 3 but was " + node.curSize);
        }
        if (node.array[0] != 1.1 || node.array[1] != 2.2 || node.array[2] != 3.3) {
            throw new AssertionError("Array contents do not match expected values");
        }
    }

    private static void testAcceptExceedBounds() {
        TestNode node = new TestNode(2);
        node.accept(1.0);
        node.accept(2.0);

        try {
            node.accept(3.0);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.equals("Accept exceeded fixed size of 2")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
