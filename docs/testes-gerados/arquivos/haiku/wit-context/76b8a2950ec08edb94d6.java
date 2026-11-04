/*
 * @test
 * @summary Verifies that the InsnList iterator next() throws NoSuchElementException when no element remains
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
            throw new AssertionError("iterator over empty list must not have next");
        }

        try {
            it.next();
            throw new AssertionError("expected NoSuchElementException on empty list");
        } catch (NoSuchElementException expected) {
        }

        try {
            it.next();
            throw new AssertionError("expected NoSuchElementException on repeated call");
        } catch (NoSuchElementException expected) {
        }
    }
}
