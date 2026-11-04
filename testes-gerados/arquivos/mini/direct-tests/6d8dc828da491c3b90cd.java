/*
 * @test
 * @summary Test InsnList.iterator(int) method behavior
 * @run main InsnListWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();

        // Initially empty list: iterator(0) should have no next or previous
        ListIterator<AbstractInsnNode> it = list.iterator(0);
        if (it.hasNext()) {
            throw new AssertionError("Empty list iterator should not have next");
        }
        if (it.hasPrevious()) {
            throw new AssertionError("Empty list iterator should not have previous");
        }

        // Add one instruction node
        AbstractInsnNode insn = new AbstractInsnNode(0) {
            @Override
            public int getType() { return 0; }
            @Override
            public void accept(jdk.internal.org.objectweb.asm.MethodVisitor mv) {}
            @Override
            public AbstractInsnNode clone(java.util.Map<LabelNode, LabelNode> map) { return this; }
        };
        list.add(insn);

        // iterator at index 0: next() returns the single element
        it = list.iterator(0);
        if (!it.hasNext()) {
            throw new AssertionError("Iterator at 0 should have next");
        }
        AbstractInsnNode next = it.next();
        if (next != insn) {
            throw new AssertionError("Iterator next() did not return the added instruction");
        }
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more next elements");
        }
        if (!it.hasPrevious()) {
            throw new AssertionError("Iterator should have previous after next()");
        }

        // iterator at index 1: previous() returns the single element
        it = list.iterator(1);
        if (!it.hasPrevious()) {
            throw new AssertionError("Iterator at 1 should have previous");
        }
        AbstractInsnNode prev = it.previous();
        if (prev != insn) {
            throw new AssertionError("Iterator previous() did not return the added instruction");
        }
        if (it.hasPrevious()) {
            throw new AssertionError("Iterator should have no more previous elements");
        }
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next after previous()");
        }

        // iterator with invalid index: negative index
        try {
            list.iterator(-1);
            throw new AssertionError("iterator(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
        }

        // iterator with invalid index: index > size
        try {
            list.iterator(2);
            throw new AssertionError("iterator(2) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
