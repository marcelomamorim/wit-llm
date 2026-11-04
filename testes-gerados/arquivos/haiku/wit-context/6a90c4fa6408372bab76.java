/*
 * @test
 * @summary Verify LinkedList.listIterator(int) positioning and bounds checking
 * @run main LinkedListWitupTest
 */
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        LinkedList<String> empty = new LinkedList<>();
        ListIterator<String> it = empty.listIterator(0);
        if (it.hasNext() || it.hasPrevious()) {
            throw new RuntimeException("empty list iterator at 0 should have no elements");
        }
        if (it.nextIndex() != 0) {
            throw new RuntimeException("nextIndex should be 0 for empty list at index 0");
        }

        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");

        it = list.listIterator(list.size());
        if (it.hasNext()) {
            throw new RuntimeException("iterator at size() should have no next element");
        }
        if (!it.hasPrevious()) {
            throw new RuntimeException("iterator at size() should have a previous element");
        }
        if (it.previousIndex() != 1) {
            throw new RuntimeException("previousIndex should be 1 at size()");
        }

        it = list.listIterator(1);
        if (it.nextIndex() != 1) {
            throw new RuntimeException("nextIndex should be 1");
        }
        if (!"b".equals(it.next())) {
            throw new RuntimeException("element at index 1 should be b");
        }

        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, list.size() + 1);
        expectIndexOutOfBounds(empty, 1);
    }

    static void expectIndexOutOfBounds(LinkedList<String> l, int index) {
        try {
            l.listIterator(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }
}
