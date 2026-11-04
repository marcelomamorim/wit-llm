/*
 * @test
 * @summary Verifies that InsnList.get(int) throws IndexOutOfBoundsException for out-of-range indexes on an empty list
 * @modules java.base/jdk.internal.org.objectweb.asm.tree
 * @run main InsnListWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();
        if (list.size() != 0) {
            throw new RuntimeException("expected empty list, size=" + list.size());
        }
        expectIndexOutOfBounds(list, 0);
        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 1);
        expectIndexOutOfBounds(list, Integer.MIN_VALUE);
        expectIndexOutOfBounds(list, Integer.MAX_VALUE);
        expectIndexOutOfBounds(list, 0);
    }

    static void expectIndexOutOfBounds(InsnList list, int index) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }
}
