/*
 * @test    @summary Test for Nodes.asPrimitiveArray() method
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testEmptyNode();
        testSingleElementNode();
        testMultipleElementsNode();
        testLargeNode();
    }

    static void testEmptyNode() {
        try {
            Nodes.emptyNode(StreamShape.REFERENCE).asPrimitiveArray();
        } catch (Exception e) {
            System.out.println("Empty node test passed with exception: " + e);
        }
    }

    static void testSingleElementNode() {
        Node<Integer> node = new Node.OfRef<>(java.util.Arrays.asList(42));
        Integer[] result = node.asPrimitiveArray();
        assert result.length == 1 : "Expected length 1";
        assert result[0] == 42 : "Expected element 42";
        System.out.println("Single element node test passed.");
    }

    static void testMultipleElementsNode() {
        Node<Integer> node = new Node.OfRef<>(java.util.Arrays.asList(1, 2, 3, 4, 5));
        Integer[] result = node.asPrimitiveArray();
        assert result.length == 5 : "Expected length 5";
        assert result[0] == 1 && result[4] == 5 : "Elements mismatch";
        System.out.println("Multiple elements node test passed.");
    }

    static void testLargeNode() {
        int size = 1000;
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Node<Integer> node = new Node.OfRef<>(list);
        Integer[] result = node.asPrimitiveArray();
        assert result.length == size : "Expected length " + size;
        assert result[0] == 0 && result[size - 1] == size - 1 : "Elements mismatch";
        System.out.println("Large node test passed.");
    }
}
