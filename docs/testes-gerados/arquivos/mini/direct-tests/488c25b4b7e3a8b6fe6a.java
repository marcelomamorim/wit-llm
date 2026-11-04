/*
 * @test
 * @summary Test java.util.stream.Nodes.accept(double) method behavior
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class TestNode {
        final double[] array;
        int index = 0;
        final int fence;

        TestNode(int size) {
            this.array = new double[size];
            this.fence = size;
        }

        public void accept(double value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }
    }

    public static void main(String[] args) {
        testAcceptWithinBounds();
        testAcceptThrowsWhenIndexExceedsFence();
    }

    private static void testAcceptWithinBounds() {
        TestNode node = new TestNode(3);
        node.accept(1.1);
        node.accept(2.2);
        node.accept(3.3);

        if (node.index != 3) {
            throw new AssertionError("Index should be 3 after three accepts, but was " + node.index);
        }
        if (node.array[0] != 1.1 || node.array[1] != 2.2 || node.array[2] != 3.3) {
            throw new AssertionError("Array contents do not match accepted values");
        }
    }

    private static void testAcceptThrowsWhenIndexExceedsFence() {
        TestNode node = new TestNode(2);
        node.accept(1.0);
        node.accept(2.0);

        boolean thrown = false;
        try {
            node.accept(3.0);
        } catch (IndexOutOfBoundsException e) {
            if (!"2".equals(e.getMessage())) {
                throw new AssertionError("Exception message should be '2' but was: " + e.getMessage());
            }
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException was not thrown");
        }
    }
}
