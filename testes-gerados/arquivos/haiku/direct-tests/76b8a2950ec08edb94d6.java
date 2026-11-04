/*
 * @test
 * @summary InsnList iterator next() must throw NoSuchElementException when no element remains
 * @modules java.base/jdk.internal.org.objectweb.asm.tree
 * @run main InsnListWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        InsnList list = new InsnList();
        ListIterator<?> it = list.iterator();

        if (it.hasNext()) {
            throw new AssertionError("empty list iterator must not have next");
        }

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("next() on empty list must throw NoSuchElementException");
        }

        thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("repeated next() on exhausted iterator must throw NoSuchElementException");
        }
    }
}
