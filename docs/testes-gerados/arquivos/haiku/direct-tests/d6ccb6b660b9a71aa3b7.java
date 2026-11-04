/*
 * @test
 * @summary Verify nextIndex() of immutable list iterators through the public listIterator API
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);

        ListIterator<Integer> it = list.listIterator();
        check(it.nextIndex() == 0, "nextIndex inicial deve ser 0");
        it.next();
        check(it.nextIndex() == 1, "nextIndex apos um next deve ser 1");
        it.next();
        it.next();
        check(it.nextIndex() == 3, "nextIndex no fim deve ser 3");

        ListIterator<Integer> it2 = list.listIterator(1);
        check(it2.nextIndex() == 1, "nextIndex com indice inicial 1 deve ser 1");

        List<Integer> empty = List.of();
        check(empty.listIterator().nextIndex() == 0, "nextIndex de lista vazia deve ser 0");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
