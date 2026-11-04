/*
 * @test
 * @summary Black-box checks for CopyOnWriteArrayList toArray and subList toArray through public API
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFullListToArray() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a", "b", "c", "d"});
        Object[] arr = list.toArray();
        check(arr.length == 4, "toArray length expected 4 but was " + arr.length);
        check(Arrays.equals(arr, new Object[] {"a", "b", "c", "d"}),
              "toArray content mismatch");
    }

    static void testToArrayIsSnapshotCopy() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        Object[] arr = list.toArray();
        arr[0] = "x";
        check("a".equals(list.get(0)), "modifying toArray result must not alter list");
    }

    static void testSubListToArray() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a", "b", "c", "d"});
        List<String> sub = list.subList(1, 3);
        Object[] arr = sub.toArray();
        check(arr.length == 2, "subList toArray length expected 2 but was " + arr.length);
        check("b".equals(arr[0]) && "c".equals(arr[1]), "subList toArray content mismatch");
    }

    static void testEmptySubListToArray() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        Object[] arr = list.subList(2, 2).toArray();
        check(arr.length == 0, "empty subList toArray must return empty array");
    }

    static void testWholeRangeSubListToArray() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        Object[] arr = list.subList(0, 3).toArray();
        check(Arrays.equals(arr, new Object[] {"a", "b", "c"}),
              "full-range subList toArray content mismatch");
    }

    static void testNullElementsPreserved() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {null, "z"});
        Object[] arr = list.subList(0, 2).toArray();
        check(arr.length == 2, "null elements: length mismatch");
        check(arr[0] == null, "null element must be preserved at index 0");
        check("z".equals(arr[1]), "non-null element mismatch at index 1");
    }

    public static void main(String[] args) {
        testFullListToArray();
        testToArrayIsSnapshotCopy();
        testSubListToArray();
        testEmptySubListToArray();
        testWholeRangeSubListToArray();
        testNullElementsPreserved();
    }
}
