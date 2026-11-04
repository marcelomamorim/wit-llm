/*
 * @test
 * @summary Verifies that ListIterator.set(Object) on an InsnList iterator replaces the node returned by next() after remove()
 * @modules java.base/jdk.internal.org.objectweb.asm.tree
 * @run main InsnListWitupTest
 */

import java.util.ListIterator;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.InsnNode;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();
        AbstractInsnNode a = new InsnNode(0);
        AbstractInsnNode b = new InsnNode(1);
        AbstractInsnNode c = new InsnNode(2);
        list.add(a);
        list.add(b);
        list.add(c);

        AbstractInsnNode r = new InsnNode(3);

        ListIterator it = list.iterator();
        AbstractInsnNode first = (AbstractInsnNode) it.next();
        if (first != a) {
            throw new RuntimeException("next() returned unexpected node");
        }
        it.remove();
        it.set(r);

        if (list.size() != 3) {
            throw new RuntimeException("unexpected size: " + list.size());
        }
        if (list.get(0) != r) {
            throw new RuntimeException("element 0 not replaced");
        }
        if (list.get(1) != b || list.get(2) != c) {
            throw new RuntimeException("other elements changed");
        }

        ListIterator check = list.iterator();
        AbstractInsnNode[] expected = { r, b, c };
        int i = 0;
        while (check.hasNext()) {
            AbstractInsnNode n = (AbstractInsnNode) check.next();
            if (i >= expected.length || n != expected[i]) {
                throw new RuntimeException("iteration mismatch at index " + i);
            }
            i++;
        }
        if (i != expected.length) {
            throw new RuntimeException("iteration length mismatch: " + i);
        }
    }
}
