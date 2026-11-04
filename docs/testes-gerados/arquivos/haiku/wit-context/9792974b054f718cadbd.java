/*
 * @test
 * @summary Verify that InsnList iterator set(...) throws IllegalStateException when no next/previous element was returned and leaves the list unchanged
 * @modules java.base/jdk.internal.org.objectweb.asm.tree
 * @run main InsnListWitupTest
 */

import java.util.ListIterator;

import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();
        ListIterator<AbstractInsnNode> it = list.iterator();

        boolean thrown = false;
        try {
            it.set(null);
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException from set() without prior next()/previous()");
        }

        if (list.size() != 0) {
            throw new AssertionError("List size changed unexpectedly: " + list.size());
        }
    }
}
