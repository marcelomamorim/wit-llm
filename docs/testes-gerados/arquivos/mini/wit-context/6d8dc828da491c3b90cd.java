/*
 * @test
 * @summary Test InsnList.iterator(int) for valid and invalid indices
 * @run main InsnListWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();

        // The list is initially empty, size() == 0
        if (list.size() != 0) {
            throw new AssertionError("Expected empty list size 0");
        }

        // iterator(0) on empty list should succeed (index == size)
        ListIterator<AbstractInsnNode> it0 = list.iterator(0);
        if (it0.hasNext()) {
            throw new AssertionError("Iterator on empty list should have no next");
        }

        // iterator with negative index should throw IndexOutOfBoundsException
        try {
            list.iterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // iterator with index > size should throw IndexOutOfBoundsException
        try {
            list.iterator(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Add a dummy AbstractInsnNode subclass instance to the list
        AbstractInsnNode dummyNode = new AbstractInsnNode(AbstractInsnNode.NOP) {
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
                return this;
            }
        };

        list.add(dummyNode);

        if (list.size() != 1) {
            throw new AssertionError("Expected list size 1 after add");
        }

        // iterator(0) should return iterator at first element
        ListIterator<AbstractInsnNode> it1 = list.iterator(0);
        if (!it1.hasNext()) {
            throw new AssertionError("Iterator at index 0 should have next");
        }
        AbstractInsnNode first = it1.next();
        if (first != dummyNode) {
            throw new AssertionError("Iterator.next() did not return the added node");
        }

        // iterator(1) should return iterator positioned after last element
        ListIterator<AbstractInsnNode> it2 = list.iterator(1);
        if (it2.hasNext()) {
            throw new AssertionError("Iterator at index 1 should have no next");
        }

        // iterator with index > size should throw IndexOutOfBoundsException
        try {
            list.iterator(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 2");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // iterator with negative index should throw IndexOutOfBoundsException
        try {
            list.iterator(-5);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -5");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
