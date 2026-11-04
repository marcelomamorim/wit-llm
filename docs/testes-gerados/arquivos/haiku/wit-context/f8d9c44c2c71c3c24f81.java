/*
 * @test
 * @summary Verifies that ListIterator.add on a CopyOnWriteArrayList sublist iterator throws UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        List<String> sub = list.subList(0, 2);
        ListIterator<String> it = sub.listIterator();

        boolean thrown = false;
        try {
            it.add("x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "ListIterator.add should throw UnsupportedOperationException");

        check(list.size() == 3, "list size changed after failed add: " + list.size());
        check("a".equals(list.get(0)) && "b".equals(list.get(1)) && "c".equals(list.get(2)),
              "list contents changed after failed add: " + list);
        check(sub.size() == 2, "sublist size changed after failed add: " + sub.size());
    }
}
