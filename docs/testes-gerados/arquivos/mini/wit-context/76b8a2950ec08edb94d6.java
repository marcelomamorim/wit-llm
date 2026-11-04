/*
 * @test
 * @summary Test deterministic behavior of InsnList.InsnListIterator.next() method
 * @run main InsnListWitupTest
 */

import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest {

    // Minimal subclass of AbstractInsnNode to allow instantiation
    static class DummyInsnNode extends AbstractInsnNode {
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
        public AbstractInsnNode clone(java.util.Map<java.util.Map.Entry<?, ?>, ?> clonedLabels) {
            return new DummyInsnNode();
        }
    }

    public static void main(String[] args) {
        InsnList list = new InsnList();

        // Add one dummy instruction to the list
        DummyInsnNode insn = new DummyInsnNode();
        list.add(insn);

        // Obtain the iterator
        java.util.ListIterator<Object> it = list.iterator();

        // Test next() returns the first instruction
        Object first = it.next();
        if (first != insn) {
            throw new AssertionError("Iterator next() did not return the expected instruction");
        }

        // After consuming the only element, next() should throw NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Iterator next() did not throw NoSuchElementException when no more elements");
        }

        // Test that size() returns 1
        if (list.size() != 1) {
            throw new AssertionError("InsnList size() should be 1 after adding one instruction");
        }

        // Test that get(0) returns the same instruction
        AbstractInsnNode getInsn = list.get(0);
        if (getInsn != insn) {
            throw new AssertionError("InsnList get(0) did not return the expected instruction");
        }

        System.out.println("All tests passed.");
    }
}
