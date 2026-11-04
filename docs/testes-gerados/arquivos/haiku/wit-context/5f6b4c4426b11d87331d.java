/*
 * @test
 * @summary WITUP-derived test: COWIterator.previous() throws NoSuchElementException when no previous element exists, and returns snapshot elements otherwise
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNoSuchElement(ListIterator<String> it, String context) {
        try {
            it.previous();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("Expected NoSuchElementException: " + context);
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});

        ListIterator<String> it = list.listIterator();
        check(!it.hasPrevious(), "fresh iterator should have no previous element");
        expectNoSuchElement(it, "previous() at cursor 0");

        check("a".equals(it.next()), "next() should return first element");
        check(it.hasPrevious(), "iterator should have previous element after next()");
        check("a".equals(it.previous()), "previous() should return first element");
        expectNoSuchElement(it, "previous() after returning to cursor 0");

        ListIterator<String> end = list.listIterator(list.size());
        check("b".equals(end.previous()), "previous() from end should return last element");
        check("a".equals(end.previous()), "second previous() should return first element");
        expectNoSuchElement(end, "previous() after exhausting backwards traversal");

        List<String> snapshotSource = new CopyOnWriteArrayList<>(new String[] {"x"});
        ListIterator<String> snap = snapshotSource.listIterator(1);
        snapshotSource.add("y");
        check("x".equals(snap.previous()), "iterator should traverse its snapshot, not later additions");
        expectNoSuchElement(snap, "previous() on exhausted snapshot iterator");
    }
}
