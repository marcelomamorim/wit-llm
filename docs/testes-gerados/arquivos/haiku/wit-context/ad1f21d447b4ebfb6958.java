/*
 * @test
 * @summary Witup-generated checks for ArrayList(int initialCapacity) constructor
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNegativeCapacityRejected(-1);
        testNegativeCapacityRejected(Integer.MIN_VALUE);
        testZeroCapacityUsable();
        testPositiveCapacityUsable();
    }

    static void testNegativeCapacityRejected(int capacity) {
        try {
            new ArrayList<String>(capacity);
            throw new RuntimeException("Expected IllegalArgumentException for capacity " + capacity);
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            check(msg != null && msg.contains("Illegal Capacity"),
                  "Unexpected message: " + msg);
        }
    }

    static void testZeroCapacityUsable() {
        ArrayList<String> list = new ArrayList<>(0);
        check(list.size() == 0, "Expected empty list for capacity 0");
        list.add("a");
        list.add(null);
        check(list.size() == 2, "Expected size 2 after two adds");
        check("a".equals(list.get(0)), "Unexpected element at index 0");
        check(list.get(1) == null, "Expected null element at index 1");
    }

    static void testPositiveCapacityUsable() {
        ArrayList<Integer> list = new ArrayList<>(16);
        check(list.size() == 0, "Expected empty list for capacity 16");
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        check(list.size() == 20, "Expected size 20");
        for (int i = 0; i < 20; i++) {
            check(list.get(i) == i, "Unexpected element at index " + i);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
