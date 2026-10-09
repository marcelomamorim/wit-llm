/* @test
   @summary Verifies the behavior of the get method in InsnList.
   @run main InsnListWitupTest_P1f185b376f73
 */
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_P1f185b376f73 {
    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
        testGetWithIndexGreaterThanSize();
    }

    private static void testGetWithValidIndex() {
        InsnList list = new InsnList();
        AbstractInsnNode node1 = new AbstractInsnNode(0) {};
        AbstractInsnNode node2 = new AbstractInsnNode(0) {};
        list.add(node1);
        list.add(node2);
        AbstractInsnNode result = list.get(1);
        if (result != node2) {
            throw new AssertionError("Expected node2 at index 1");
        }
    }

    private static void testGetWithNegativeIndex() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testGetWithIndexEqualToSize() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(list.size());
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        }
    }

    private static void testGetWithIndexGreaterThanSize() {
        InsnList list = new InsnList();
        list.add(new AbstractInsnNode(0) {});
        boolean thrown = false;
        try {
            list.get(10);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
