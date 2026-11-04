/*
 * @test
 * @summary Checks that Arrays.asList(...).forEach rejects a null action and visits elements in order
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionThrowsNpeForEmptyList();
        testIterationOrder();
        testEmptyListVisitsNothing();
    }

    static void testNullActionThrowsNpe() {
        List<String> list = Arrays.asList("a", "b", "c");
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null action");
        }
    }

    static void testNullActionThrowsNpeForEmptyList() {
        List<String> list = Arrays.<String>asList();
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null action on empty list");
        }
    }

    static void testIterationOrder() {
        List<String> list = Arrays.asList("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append(s));
        if (!sb.toString().equals("abc")) {
            throw new RuntimeException("Unexpected iteration result: " + sb);
        }
    }

    static void testEmptyListVisitsNothing() {
        List<String> list = Arrays.<String>asList();
        int[] count = new int[1];
        list.forEach(s -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("Expected no visits, got " + count[0]);
        }
    }
}
