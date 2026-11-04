/*
 * @test
 * @summary Verifies toArray() on a CopyOnWriteArrayList subList returns a correct, independent Object[] copy
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c", "d"});

        List<String> sub = list.subList(1, 3);
        Object[] arr = sub.toArray();
        check(arr.getClass() == Object[].class, "toArray must return Object[]");
        check(arr.length == 2, "expected length 2 but was " + arr.length);
        check("b".equals(arr[0]), "arr[0] should be b");
        check("c".equals(arr[1]), "arr[1] should be c");

        // The returned array must be a copy, not a view of the backing array.
        arr[0] = "X";
        check("b".equals(list.get(1)), "list must not be affected by modifying toArray result");
        check("b".equals(sub.get(0)), "sublist must not be affected by modifying toArray result");

        Object[] empty = list.subList(2, 2).toArray();
        check(empty.length == 0, "empty sublist must yield empty array");

        Object[] all = list.subList(0, 4).toArray();
        check(all.length == 4, "full sublist length expected 4 but was " + all.length);
        check("a".equals(all[0]) && "d".equals(all[3]), "full sublist content mismatch");

        CopyOnWriteArrayList<String> withNull =
            new CopyOnWriteArrayList<>(new String[]{null, "x"});
        Object[] na = withNull.subList(0, 2).toArray();
        check(na.length == 2, "null-containing sublist length mismatch");
        check(na[0] == null, "null element must be preserved");
        check("x".equals(na[1]), "non-null element mismatch");

        System.out.println("PASS");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
