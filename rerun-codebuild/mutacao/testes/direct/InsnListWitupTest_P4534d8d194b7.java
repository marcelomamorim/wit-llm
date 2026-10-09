/* @test    @summary Test the get method of InsnList for valid and invalid indices    @run main InsnListWitupTest_P4534d8d194b7 */

import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_P4534d8d194b7 {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetNegativeIndex();
        testGetIndexEqualSize();
    }

    static void testGetValidIndex() {
        InsnList list = new InsnList();
        AbstractInsnNode node1 = new AbstractInsnNode(0) {};
        AbstractInsnNode node2 = new AbstractInsnNode(0) {};
        list.add(node1);
        list.add(node2);
        AbstractInsnNode result = list.get(1);
        assert result == node2 : "Expected node2 at index 1";
    }

    static void testGetNegativeIndex() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assert thrown : "Expected IndexOutOfBoundsException for negative index";
    }

    static void testGetIndexEqualSize() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assert thrown : "Expected IndexOutOfBoundsException for index equal to size";
    }
}
