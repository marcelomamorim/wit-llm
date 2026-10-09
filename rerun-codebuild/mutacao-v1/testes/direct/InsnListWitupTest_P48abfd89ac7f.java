/* @test    @summary Test for InsnList.get method to ensure correct node retrieval and exception handling    @run main InsnListWitupTest_P48abfd89ac7f */

import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_P48abfd89ac7f {

    public static void main(String[] args) {
        try {
            testGetValidIndex();
            testGetNegativeIndex();
            testGetIndexEqualSize();
            testGetIndexGreaterSize();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static void testGetValidIndex() {
        InsnList list = new InsnList();
        AbstractInsnNode node1 = new AbstractInsnNode(0) {};
        AbstractInsnNode node2 = new AbstractInsnNode(0) {};
        list.add(node1);
        list.add(node2);
        AbstractInsnNode result = list.get(1);
        if (result != node2) {
            throw new AssertionError("Expected node2 at index 1");
        }
    }

    private static void testGetNegativeIndex() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testGetIndexEqualSize() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        }
    }

    private static void testGetIndexGreaterSize() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
