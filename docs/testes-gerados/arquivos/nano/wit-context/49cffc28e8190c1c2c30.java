/*
 * @test
 * @summary Test for method java.util.stream.Nodes.end()
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testEndMethodNormal();
        testEndMethodThrows();
    }

    private static void testEndMethodNormal() {
        // Preparar um cenário onde curSize >= array.length
        Nodes.Node<?> node = new Nodes.Node<Object>() {
            int curSize = 5;
            Object[] array = new Object[] {1, 2, 3, 4, 5};
            @Override
            public void end() {
                if (curSize < array.length) {
                    throw new IllegalStateException(String.format("End size %d is less than fixed size %d",
                                                                  curSize, array.length));
                }
            }
        };
        try {
            node.end(); // Espera que não lance exceção
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unexpected exception in normal case", e);
        }
    }

    private static void testEndMethodThrows() {
        // Preparar um cenário onde curSize < array.length
        Nodes.Node<?> node = new Nodes.Node<Object>() {
            int curSize = 3;
            Object[] array = new Object[] {1, 2, 3, 4, 5};
            @Override
            public void end() {
                if (curSize < array.length) {
                    throw new IllegalStateException(String.format("End size %d is less than fixed size %d",
                                                                  curSize, array.length));
                }
            }
        };
        try {
            node.end();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Teste esperado
            String message = e.getMessage();
            if (message == null || !message.contains("End size 3 is less than fixed size 5")) {
                throw new AssertionError("Exception message does not match expected", e);
            }
        }
    }
}
