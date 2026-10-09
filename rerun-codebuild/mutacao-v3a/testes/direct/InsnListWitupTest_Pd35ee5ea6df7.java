/*
 * @test
 * @summary Test the get method of InsnList for valid and invalid indices
 * @run main InsnListWitupTest_Pd35ee5ea6df7
 */
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_Pd35ee5ea6df7 {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetNegativeIndex();
        testGetOutOfBoundsIndex();
    }

    private static void testGetValidIndex() {
        InsnList list = new InsnList();
        AbstractInsnNode node1 = new AbstractInsnNode(0) {
            @Override
            public int getOpcode() { return 0; }
        };
        AbstractInsnNode node2 = new AbstractInsnNode(0) {
            @Override
            public int getOpcode() { return 0; }
        };
        list.add(node1);
        list.add(node2);
        AbstractInsnNode result = list.get(1);
        if (result != node2) {
            throw new AssertionError("Expected node2 at index 1");
        }
    }

    private static void testGetNegativeIndex() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {
            @Override
            public int getOpcode() { return 0; }
        });
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

    private static void testGetOutOfBoundsIndex() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {
            @Override
            public int getOpcode() { return 0; }
        });
        boolean thrown = false;
        try {
            list.get(1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for out-of-bounds index");
        }
    }
}
