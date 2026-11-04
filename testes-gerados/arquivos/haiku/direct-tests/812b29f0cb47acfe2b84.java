/*
 * @test
 * @summary Verify that the remove() method of the empty iterators returned by Collections throws IllegalStateException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testEmptyIteratorRemoveThrowsISE();
        testEmptyIteratorRemoveRepeatedlyThrowsISE();
        testEmptyIteratorHasNoElements();
        testEmptyListIteratorRemoveThrowsISE();
    }

    static void testEmptyIteratorRemoveThrowsISE() {
        Iterator<String> it = Collections.<String>emptyIterator();
        expectIllegalState(it, "emptyIterator.remove() on fresh iterator");
    }

    static void testEmptyIteratorRemoveRepeatedlyThrowsISE() {
        Iterator<Integer> it = Collections.<Integer>emptyIterator();
        expectIllegalState(it, "emptyIterator.remove() first call");
        expectIllegalState(it, "emptyIterator.remove() second call");
    }

    static void testEmptyIteratorHasNoElements() {
        Iterator<String> it = Collections.<String>emptyIterator();
        if (it.hasNext()) {
            throw new RuntimeException("emptyIterator should have no elements");
        }
        expectIllegalState(it, "emptyIterator.remove() after hasNext()");
    }

    static void testEmptyListIteratorRemoveThrowsISE() {
        ListIterator<String> it = Collections.<String>emptyListIterator();
        expectIllegalState(it, "emptyListIterator.remove()");
    }

    static void expectIllegalState(Iterator<?> it, String name) {
        try {
            it.remove();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException(name + ": expected IllegalStateException");
    }
}
