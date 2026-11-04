/*
 * @test
 * @summary Verify that ImmutableCollections list iterator remove() always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Iterator;
import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextThrows();
        testRemoveOnEmptyListIteratorThrows();
        testIteratorStateUnchangedAfterFailedRemove();
    }

    static void testRemoveBeforeNextThrows() {
        List<Integer> list = List.of(1, 2, 3);
        Iterator<Integer> it = list.iterator();
        expectUoe(it);
    }

    static void testRemoveAfterNextThrows() {
        List<Integer> list = List.of(1, 2, 3);
        Iterator<Integer> it = list.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("expected element");
        }
        if (it.next().intValue() != 1) {
            throw new AssertionError("unexpected first element");
        }
        expectUoe(it);
    }

    static void testRemoveOnEmptyListIteratorThrows() {
        List<Integer> list = List.of();
        Iterator<Integer> it = list.iterator();
        expectUoe(it);
    }

    static void testIteratorStateUnchangedAfterFailedRemove() {
        List<Integer> list = List.of(1, 2, 3);
        Iterator<Integer> it = list.iterator();
        it.next();
        expectUoe(it);
        if (!it.hasNext()) {
            throw new AssertionError("iterator should still have elements");
        }
        if (it.next().intValue() != 2) {
            throw new AssertionError("iterator position changed by failed remove");
        }
        if (list.size() != 3) {
            throw new AssertionError("list size changed");
        }
    }

    static void expectUoe(Iterator<?> it) {
        try {
            it.remove();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("remove() did not throw UnsupportedOperationException");
    }
}
