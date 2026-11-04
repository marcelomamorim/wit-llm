/*
 * @test
 * @summary Checks ListIterator.previousIndex() behavior of immutable list iterators via public List API
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);

        ListIterator<Integer> it0 = list.listIterator(0);
        check(it0.previousIndex() == -1, "previousIndex at start should be -1");

        ListIterator<Integer> it2 = list.listIterator(2);
        check(it2.previousIndex() == 1, "previousIndex at index 2 should be 1");

        ListIterator<Integer> itEnd = list.listIterator(list.size());
        check(itEnd.previousIndex() == 2, "previousIndex at end should be size-1");

        ListIterator<Integer> it1 = list.listIterator();
        check(it1.previousIndex() == -1, "default listIterator previousIndex should be -1");
        it1.next();
        check(it1.previousIndex() == 0, "previousIndex after next should be 0");

        Iterator<Integer> plain = list.iterator();
        if (plain instanceof ListIterator) {
            boolean threw = false;
            try {
                ((ListIterator<Integer>) plain).previousIndex();
            } catch (UnsupportedOperationException e) {
                threw = true;
            }
            check(threw, "previousIndex on non-list iterator should throw UnsupportedOperationException");
        }
    }
}
