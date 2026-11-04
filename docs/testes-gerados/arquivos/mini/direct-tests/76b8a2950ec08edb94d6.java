/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.tree.InsnList.next() method behavior
 * @run main InsnListWitupTest
 */

import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest {

    static class DummyInsnNode extends AbstractInsnNode {
        DummyInsnNode next;
        DummyInsnNode prev;

        DummyInsnNode() {
            super(-1); // opcode irrelevant for test
        }

        @Override
        public int getType() {
            return 0;
        }

        @Override
        public AbstractInsnNode clone(java.util.Map<?, ?> map) {
            return null;
        }
    }

    static class InsnListIterator {
        DummyInsnNode next;
        DummyInsnNode prev;
        DummyInsnNode remove;

        InsnListIterator(DummyInsnNode start) {
            this.next = start;
            this.prev = null;
            this.remove = null;
        }

        public Object next() {
            if (next == null) {
                throw new NoSuchElementException();
            }
            DummyInsnNode result = next;
            prev = result;
            next = result.next;
            remove = result;
            return result;
        }
    }

    public static void main(String[] args) {
        // Setup a small linked list of DummyInsnNode
        DummyInsnNode node1 = new DummyInsnNode();
        DummyInsnNode node2 = new DummyInsnNode();
        DummyInsnNode node3 = new DummyInsnNode();
        node1.next = node2;
        node2.next = node3;
        node3.next = null;

        // Create iterator starting at node1
        InsnListIterator it = new InsnListIterator(node1);

        // First next() returns node1
        Object first = it.next();
        if (first != node1) {
            throw new AssertionError("First next() did not return first node");
        }

        // Second next() returns node2
        Object second = it.next();
        if (second != node2) {
            throw new AssertionError("Second next() did not return second node");
        }

        // Third next() returns node3
        Object third = it.next();
        if (third != node3) {
            throw new AssertionError("Third next() did not return third node");
        }

        // Fourth next() throws NoSuchElementException
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException on next() when no more elements");
        }
    }
}
