/*
 * @test
 * @summary Verifies write-through, ordering, null handling and null-operator rejection of Arrays.asList(...).replaceAll(UnaryOperator)
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testWriteThrough();
        testOrderAndCalls();
        testNullElements();
        testEmptyArray();
        testNullOperator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testWriteThrough() {
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);
        list.replaceAll(s -> s.toUpperCase());
        check(arr[0].equals("A"), "arr[0] not replaced");
        check(arr[1].equals("B"), "arr[1] not replaced");
        check(arr[2].equals("C"), "arr[2] not replaced");
        check(list.get(0).equals("A"), "list view not updated");
    }

    static void testOrderAndCalls() {
        String[] arr = {"x", "y", "z"};
        List<String> list = Arrays.asList(arr);
        StringBuilder seen = new StringBuilder();
        list.replaceAll(s -> {
            seen.append(s);
            return s + "!";
        });
        check(seen.toString().equals("xyz"), "operator not applied in index order: " + seen);
        check(arr[0].equals("x!") && arr[1].equals("y!") && arr[2].equals("z!"),
              "unexpected results after replaceAll");
    }

    static void testNullElements() {
        Integer[] arr = {1, null, 3};
        List<Integer> list = Arrays.asList(arr);
        list.replaceAll(v -> v == null ? 0 : v * 2);
        check(arr[0] == 2, "element 0 wrong: " + arr[0]);
        check(arr[1] == 0, "null element not passed to operator correctly");
        check(arr[2] == 6, "element 2 wrong: " + arr[2]);
    }

    static void testEmptyArray() {
        String[] arr = {};
        List<String> list = Arrays.asList(arr);
        int[] count = {0};
        list.replaceAll(s -> {
            count[0]++;
            return s;
        });
        check(count[0] == 0, "operator invoked on empty array");
        check(arr.length == 0, "empty array length changed");
    }

    static void testNullOperator() {
        String[] arr = {"a", "b"};
        List<String> list = Arrays.asList(arr);
        boolean thrown = false;
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null operator did not throw NullPointerException");
        check(arr[0].equals("a") && arr[1].equals("b"), "array modified despite null operator");
    }
}
