/* @test    @summary Test for Nodes.accept(double) method behavior and exception handling    @run main NodesWitupTest */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptIndexOutOfBounds();
    }

    static void testAcceptNormal() {
        double[] array = new double[5];
        Nodes node = new Nodes(array);
        node.accept(3.14);
        assert array[0] == 3.14 : "Expected 3.14 at index 0";
        assert node.index == 1 : "Index should be incremented to 1";
    }

    static void testAcceptIndexOutOfBounds() {
        double[] array = new double[1];
        Nodes node = new Nodes(array);
        node.accept(1.23);
        boolean thrown = false;
        try {
            node.accept(4.56);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
            assert e.getMessage().equals("1") : "Exception message should be '1'";
        }
        assert thrown : "IndexOutOfBoundsException should be thrown when index exceeds fence";
    }

    static class Nodes {
        double[] array;
        int index = 0;
        int fence;

        Nodes(double[] array) {
            this.array = array;
            this.fence = array.length;
        }

        public void accept(double value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }
    }
}
