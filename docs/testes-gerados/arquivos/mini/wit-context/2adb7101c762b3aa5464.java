/*
 * @test
 * @summary Deterministic tests for java.util.stream.Nodes.copyInto(T[], int)
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testCopyIntoWithValidArray();
        testCopyIntoWithInvalidOffset();
    }

    static void testCopyIntoWithValidArray() {
        // Create a SpinedNodeBuilder instance via anonymous subclass to access copyInto
        Nodes.SpinedNodeBuilder<String> builder = new Nodes.SpinedNodeBuilder<String>() {
            {
                begin(1); // allocate space for 1 element
                accept("test");
                end();
            }
        };

        String[] array = new String[1];
        builder.copyInto(array, 0);

        if (!"test".equals(array[0])) {
            throw new AssertionError("copyInto did not copy element correctly");
        }
    }

    static void testCopyIntoWithInvalidOffset() {
        Nodes.SpinedNodeBuilder<String> builder = new Nodes.SpinedNodeBuilder<String>() {
            {
                begin(1);
                accept("test");
                end();
            }
        };

        String[] array = new String[1];

        // offset too large: should throw IndexOutOfBoundsException
        boolean thrown = false;
        try {
            builder.copyInto(array, 1); // offset 1 with array length 1 and elementIndex 1 -> out of bounds
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for offset too large");
        }

        // offset negative: should throw IndexOutOfBoundsException
        thrown = false;
        try {
            builder.copyInto(array, -1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        }
    }
}
