/*
 * @test
 * @summary Verifies boundary behavior of InsnList.iterator(int) on an empty list
 * @modules java.base/jdk.internal.org.objectweb.asm.tree
 * @run main InsnListWitupTest
 */
import java.util.ListIterator;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();

        ListIterator<AbstractInsnNode> it = list.iterator(0);
        check(it != null, "iterator(0) returned null");
        check(!it.hasNext(), "iterator(0) on empty list should have no next");
        check(!it.hasPrevious(), "iterator(0) on empty list should have no previous");

        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 1);
    }

    static void expectIndexOutOfBounds(InsnList list, int index) {
        try {
            list.iterator(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
