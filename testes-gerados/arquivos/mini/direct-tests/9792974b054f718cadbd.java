/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.tree.InsnList$ListIterator.set method behavior
 * @run main InsnListWitupTest
 */

import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        testSetWithoutRemoveThrows();
        testSetReplacesCorrectly();
    }

    private static void testSetWithoutRemoveThrows() {
        InsnList list = new InsnList();
        InsnList.ListIterator it = list.iterator();
        try {
            it.set(new DummyInsnNode());
            throw new AssertionError("Expected IllegalStateException when set called without prior remove");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testSetReplacesCorrectly() {
        InsnList list = new InsnList();
        AbstractInsnNode insn1 = new DummyInsnNode();
        AbstractInsnNode insn2 = new DummyInsnNode();
        list.add(insn1);
        list.add(insn2);

        InsnList.ListIterator it = list.iterator();
        it.next(); // position on insn1
        it.remove(); // remove insn1

        AbstractInsnNode replacement = new DummyInsnNode();
        it.set(replacement); // should replace insn1 with replacement

        // Verify list contains replacement and insn2
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after set");
        }
        if (list.get(0) != replacement) {
            throw new AssertionError("First element should be replacement after set");
        }
        if (list.get(1) != insn2) {
            throw new AssertionError("Second element should be original second element");
        }
    }

    // Minimal dummy subclass to instantiate AbstractInsnNode
    private static class DummyInsnNode extends AbstractInsnNode {
        DummyInsnNode() {
            super(-1); // opcode -1 means no opcode
        }

        @Override
        public int getType() {
            return 0;
        }

        @Override
        public void accept(jdk.internal.org.objectweb.asm.MethodVisitor mv) {
            // no-op
        }

        @Override
        public AbstractInsnNode clone(java.util.Map<LabelNode, LabelNode> labels) {
            return new DummyInsnNode();
        }
    }
}
