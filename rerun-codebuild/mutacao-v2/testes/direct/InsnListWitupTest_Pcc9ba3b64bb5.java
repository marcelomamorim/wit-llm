/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.tree.InsnList.get(int) method
 * @run main InsnListWitupTest_Pcc9ba3b64bb5
 */

import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_Pcc9ba3b64bb5 {

    public static void main(String[] args) {
        InsnList list = new InsnList();

        // Test get on empty list: expect IndexOutOfBoundsException
        try {
            list.get(0);
            throw new Error("Expected IndexOutOfBoundsException for get(0) on empty list");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Add a dummy AbstractInsnNode subclass instance
        AbstractInsnNode node = new AbstractInsnNode(0) {
            @Override
            public int getType() {
                return 0;
            }

            @Override
            public void accept(jdk.internal.org.objectweb.asm.MethodVisitor mv) {
            }

            @Override
            public AbstractInsnNode clone(java.util.Map<LabelNode, LabelNode> labels) {
                return this;
            }
        };

        list.add(node);

        // Test get with valid index 0
        AbstractInsnNode result = list.get(0);
        if (result != node) {
            throw new Error("get(0) did not return the expected node");
        }

        // Test get with negative index: expect IndexOutOfBoundsException
        try {
            list.get(-1);
            throw new Error("Expected IndexOutOfBoundsException for get(-1)");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test get with index >= size: expect IndexOutOfBoundsException
        try {
            list.get(1);
            throw new Error("Expected IndexOutOfBoundsException for get(1)");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
