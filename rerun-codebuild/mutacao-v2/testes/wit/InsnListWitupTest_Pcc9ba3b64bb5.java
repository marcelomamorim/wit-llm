/*
 * @test
 * @summary Test InsnList.get(int) method for valid and invalid indices
 * @run main InsnListWitupTest_Pcc9ba3b64bb5
 */

import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_Pcc9ba3b64bb5 {

    public static void main(String[] args) {
        InsnList list = new InsnList();

        // The list is initially empty, get(0) should throw IndexOutOfBoundsException
        try {
            list.get(0);
            throw new Error("Expected IndexOutOfBoundsException for get(0) on empty list");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Negative index should throw IndexOutOfBoundsException
        try {
            list.get(-1);
            throw new Error("Expected IndexOutOfBoundsException for get(-1)");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Add a single dummy AbstractInsnNode subclass instance to the list
        AbstractInsnNode dummyNode = new AbstractInsnNode(0) {
            @Override
            public int getType() {
                return 0;
            }

            @Override
            public void accept(jdk.internal.org.objectweb.asm.MethodVisitor mv) {
                // no-op
            }

            @Override
            public AbstractInsnNode clone(java.util.Map<java.util.Map.Entry<?, ?>, ?> clonedLabels) {
                return this; // dummy clone
            }
        };

        list.add(dummyNode);

        // get(0) should return the node just added
        AbstractInsnNode result = list.get(0);
        if (result != dummyNode) {
            throw new Error("get(0) did not return the expected node");
        }

        // get(1) should throw IndexOutOfBoundsException
        try {
            list.get(1);
            throw new Error("Expected IndexOutOfBoundsException for get(1) when size is 1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        System.out.println("All InsnList.get(int) tests passed.");
    }
}
