/*
 * @test
 * @summary Tests for java.util.stream.Nodes.copyInto(T[] array, int offset)
 * @run main NodesCopyIntoTest
 */

import java.util.stream.Nodes;
import java.util.Arrays;

public class NodesCopyIntoTest {

    public static void main(String[] args) {
        testCopyIntoNormal();
        testCopyIntoWithNegativeOffset();
        testCopyIntoWithLargeOffset();
    }

    static void testCopyIntoNormal() {
        Integer[] source = {1, 2, 3};
        Integer[] target = new Integer[5];
        Arrays.fill(target, null);
        Nodes.copyInto(target, 1, source, 0, source.length);
        assert target[0] == null : "First element should be null";
        assert target[1] == 1 : "Element at index 1 should be 1";
        assert target[2] == 2 : "Element at index 2 should be 2";
        assert target[3] == 3 : "Element at index 3 should be 3";
        assert target[4] == null : "Last element should be null";
    }

    static void testCopyIntoWithNegativeOffset() {
        Integer[] source = {4, 5};
        Integer[] target = new Integer[4];
        Arrays.fill(target, null);
        try {
            Nodes.copyInto(target, -1, source, 0, source.length);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCopyIntoWithLargeOffset() {
        Integer[] source = {6};
        Integer[] target = new Integer[2];
        Arrays.fill(target, null);
        try {
            Nodes.copyInto(target, 2, source, 0, source.length);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
