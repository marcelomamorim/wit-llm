/* @test    @summary Test iterator method of InsnList class    @run main InsnListWitupTest */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        testIteratorReturnsCorrectType();
        testIteratorAtSpecificIndex();
        testIteratorThrowsExceptionAtInvalidIndex();
    }

    static void testIteratorReturnsCorrectType() {
        InsnList list = new InsnList();
        ListIterator<jdk.internal.org.objectweb.asm.tree.AbstractInsnNode> iterator = list.iterator(0);
        assert iterator != null : "Iterator should not be null";
        assert iterator instanceof ListIterator : "Iterator should be instance of ListIterator";
    }

    static void testIteratorAtSpecificIndex() {
        InsnList list = new InsnList();
        // Since InsnList is empty, index 0 is valid for empty list
        ListIterator<jdk.internal.org.objectweb.asm.tree.AbstractInsnNode> iterator = list.iterator(0);
        assert iterator.hasNext() == false : "Empty list iterator should have no next element";
        try {
            iterator.next();
            assert false : "Calling next() on empty iterator should throw";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testIteratorThrowsExceptionAtInvalidIndex() {
        InsnList list = new InsnList();
        try {
            list.iterator(-1);
            assert false : "Negative index should throw IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            list.iterator(1);
            assert false : "Index greater than size should throw IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
