/*
 * @test
 * @summary ListIterator.set on immutable list iterators must throw UnsupportedOperationException and leave the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        checkSetThrows(List.of(), null);
        checkSetThrows(List.of(1), 1);
        checkSetThrows(List.of(1, 2), 2);
        checkSetThrows(List.of(1, 2, 3, 4, 5), 3);
        checkSetThrows(List.of("a", "b", "c"), "x");
        checkSetAfterNextThrows(List.of("a", "b", "c"), "y");
    }

    static <E> void checkSetThrows(List<E> list, E value) {
        List<E> original = List.copyOf(list);
        ListIterator<E> it = list.listIterator();
        boolean thrown = false;
        try {
            it.set(value);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from set");
        }
        if (!list.equals(original)) {
            throw new RuntimeException("list changed after failed set");
        }
    }

    static <E> void checkSetAfterNextThrows(List<E> list, E value) {
        List<E> original = List.copyOf(list);
        ListIterator<E> it = list.listIterator();
        if (it.hasNext()) {
            it.next();
        }
        boolean thrown = false;
        try {
            it.set(value);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from set after next");
        }
        if (!list.equals(original)) {
            throw new RuntimeException("list changed after failed set after next");
        }
    }
}
