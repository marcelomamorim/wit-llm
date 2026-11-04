/*
 * @test
 * @summary Verifies ArrayList.SubList.toArray() returns a detached copy of the sublist range and detects comodification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        List<String> sub = list.subList(1, 3);
        Object[] arr = sub.toArray();
        check(arr.length == 2, "expected length 2 but was " + arr.length);
        check("b".equals(arr[0]), "expected first element b");
        check("c".equals(arr[1]), "expected second element c");

        arr[0] = "x";
        check("b".equals(list.get(1)), "toArray must return a copy, backing list was modified");

        Object[] empty = list.subList(2, 2).toArray();
        check(empty.length == 0, "expected empty array for empty sublist");

        ArrayList<String> withNull = new ArrayList<>();
        withNull.add(null);
        withNull.add("z");
        Object[] nullArr = withNull.subList(0, 2).toArray();
        check(nullArr.length == 2, "expected length 2 with null element");
        check(nullArr[0] == null, "expected null first element");
        check("z".equals(nullArr[1]), "expected z as second element");

        List<String> stale = list.subList(0, 2);
        list.add("e");
        boolean thrown = false;
        try {
            stale.toArray();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
