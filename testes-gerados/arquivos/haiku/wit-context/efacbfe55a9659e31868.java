/*
 * @test
 * @summary Verifies ArrayList.listIterator(int) bounds checking and iterator positioning
 * @run main ArrayListWitupTest
 */
import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> atEnd = list.listIterator(list.size());
        check(!atEnd.hasNext(), "hasNext must be false at size()");
        check(atEnd.previousIndex() == 2, "previousIndex must be size()-1 at size()");

        ListIterator<String> atStart = list.listIterator(0);
        check(atStart.hasNext(), "hasNext must be true at index 0");
        check("a".equals(atStart.next()), "first element must be 'a'");

        check(list.listIterator(1).nextIndex() == 1, "nextIndex must equal requested index");

        ArrayList<String> empty = new ArrayList<>();
        check(!empty.listIterator(0).hasNext(), "empty list iterator must not have next");

        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, list.size() + 1);
        expectIndexOutOfBounds(list, Integer.MIN_VALUE);
        expectIndexOutOfBounds(empty, 1);
    }

    static void expectIndexOutOfBounds(ArrayList<String> list, int index) {
        try {
            list.listIterator(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
