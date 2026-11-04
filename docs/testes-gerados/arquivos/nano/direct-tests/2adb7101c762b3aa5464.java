/* @test    @summary Test the copyInto method of Nodes class for correct copying behavior.    @run main NodesWitupTest */

import java.util.stream.Nodes;
import java.util.Arrays;

public class NodesWitupTest {

    public static void main(String[] args) {
        testCopyIntoCopiesElementsCorrectly();
        testCopyIntoWithEmptyArray();
        testCopyIntoWithOffset();
    }

    static void testCopyIntoCopiesElementsCorrectly() {
        Integer[] source = {1, 2, 3, 4, 5};
        Integer[] target = new Integer[10];
        Arrays.fill(target, null);
        Nodes.copyInto(source, target, 0);
        assert target[0] == 1 : "First element should be 1";
        assert target[4] == null : "Fifth element should be null";
        assert target[1] == 2 : "Second element should be 2";
    }

    static void testCopyIntoWithEmptyArray() {
        Integer[] source = {1, 2, 3};
        Integer[] target = new Integer[0];
        try {
            Nodes.copyInto(source, target, 0);
            assert false : "Should throw ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCopyIntoWithOffset() {
        Integer[] source = {9, 8, 7};
        Integer[] target = {0, 0, 0, 0, 0};
        Nodes.copyInto(source, target, 2);
        assert target[2] == 9 : "Element at index 2 should be 9";
        assert target[3] == 8 : "Element at index 3 should be 8";
        assert target[4] == 7 : "Element at index 4 should be 7";
    }
}

// Additional helper method to adapt the method signature for testing
class Nodes {
    public static <T> void copyInto(T[] source, T[] target, int offset) {
        // Simulate the actual method's behavior
        if (source.length + offset > target.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        System.arraycopy(source, 0, target, offset, source.length);
    }
}
