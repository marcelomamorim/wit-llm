/*
 * @test
 * @summary Verifies that the iterator returned by Collections.emptyListIterator() rejects set() with IllegalStateException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        ListIterator<String> it = Collections.emptyListIterator();

        if (it.hasNext()) {
            throw new AssertionError("hasNext() should be false for empty iterator");
        }
        if (it.hasPrevious()) {
            throw new AssertionError("hasPrevious() should be false for empty iterator");
        }
        if (it.nextIndex() != 0) {
            throw new AssertionError("nextIndex() should be 0, was " + it.nextIndex());
        }
        if (it.previousIndex() != -1) {
            throw new AssertionError("previousIndex() should be -1, was " + it.previousIndex());
        }

        expectSetThrowsIllegalState(it, "x");
        expectSetThrowsIllegalState(it, null);

        ListIterator<Integer> intIt = Collections.emptyListIterator();
        try {
            intIt.set(Integer.valueOf(1));
            throw new AssertionError("set() should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void expectSetThrowsIllegalState(ListIterator<String> it, String value) {
        try {
            it.set(value);
            throw new AssertionError("set(" + value + ") should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
