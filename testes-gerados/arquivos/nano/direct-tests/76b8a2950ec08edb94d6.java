/*
 * @test
 * @summary Test the next() method of InsnList for correct behavior and exception handling.
 * @run main InsnListWitupTest
 */

import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        testNextWithNullNext();
        testNextWithValidNext();
    }

    private static void testNextWithNullNext() {
        InsnList list = new InsnList();
        // Using reflection to set private field 'next' to null, simulating end of list
        try {
            java.lang.reflect.Field nextField = InsnList.class.getDeclaredField("next");
            nextField.setAccessible(true);
            nextField.set(list, null);
            // Call next(), expecting NoSuchElementException
            list.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }

    private static void testNextWithValidNext() {
        InsnList list = new InsnList();
        // Create a dummy AbstractInsnNode with a 'next' field pointing to a new node
        class DummyInsnNode extends jdk.internal.org.objectweb.asm.tree.AbstractInsnNode {
            protected DummyInsnNode() { super(); }
            public void accept(jdk.internal.org.objectweb.asm.MethodVisitor mv) {}
            public int getType() { return 0; }
        }
        DummyInsnNode node1 = new DummyInsnNode();
        DummyInsnNode node2 = new DummyInsnNode();
        try {
            java.lang.reflect.Field nextField = InsnList.class.getDeclaredField("next");
            nextField.setAccessible(true);
            nextField.set(list, node2);
            // Also set node2's 'next' to null to simulate end
            java.lang.reflect.Field nodeNextField = jdk.internal.org.objectweb.asm.tree.AbstractInsnNode.class.getDeclaredField("next");
            nodeNextField.setAccessible(true);
            nodeNextField.set(node2, null);
            // Set list's 'next' to node1
            nextField.set(list, node1);
            // Call next(), should return node1
            Object result = list.next();
            if (result != node1) {
                throw new AssertionError("next() did not return the expected node.");
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }
}
