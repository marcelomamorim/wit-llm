/*
 * @test
 * @summary Verifies InsnList.iterator(int) returns a usable iterator on an empty list
 * @modules java.base/jdk.internal.org.objectweb.asm.tree
 * @run main InsnListWitupTest
 */
import java.util.ListIterator;
import java.util.NoSuchElementException;

import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();
        ListIterator<AbstractInsnNode> it = list.iterator(0);
        if (it == null) {
            throw new RuntimeException("iterator(0) returned null");
        }
        if (it.hasNext()) {
            throw new RuntimeException("hasNext() should be false on empty list");
        }
        if (it.hasPrevious()) {
            throw new RuntimeException("hasPrevious() should be false at index 0");
        }
        if (it.nextIndex() != 0) {
            throw new RuntimeException("nextIndex() expected 0 but was " + it.nextIndex());
        }
        if (it.previousIndex() != -1) {
            throw new RuntimeException("previousIndex() expected -1 but was " + it.previousIndex());
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("next() on empty iterator should throw NoSuchElementException");
        }
    }
}
