/*
 * @test
 * @summary Verifies ArrayList(int initialCapacity) accepts zero and positive capacities and rejects negative capacities with IllegalArgumentException
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testZeroCapacityAcceptsAdd();
        testPositiveCapacityStartsEmpty();
        testPositiveCapacityStoresElements();
        testCapacityOneAcceptsNull();
        testNegativeCapacityThrows();
        testMinValueCapacityThrows();
    }

    static void testZeroCapacityAcceptsAdd() {
        List<String> list = new ArrayList<String>(0);
        check(list.size() == 0, "zero capacity list should be empty");
        check(list.isEmpty(), "zero capacity list should report isEmpty");
        list.add("a");
        check(list.size() == 1, "size should be 1 after add");
        check("a".equals(list.get(0)), "get(0) should return added element");
    }

    static void testPositiveCapacityStartsEmpty() {
        List<Integer> list = new ArrayList<Integer>(10);
        check(list.size() == 0, "positive capacity list should start empty");
        check(list.isEmpty(), "positive capacity list should report isEmpty");
    }

    static void testPositiveCapacityStoresElements() {
        List<Integer> list = new ArrayList<Integer>(10);
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        check(list.size() == 20, "size should be 20 after 20 adds");
        for (int i = 0; i < 20; i++) {
            check(list.get(i).intValue() == i, "element at index " + i + " should be " + i);
        }
    }

    static void testCapacityOneAcceptsNull() {
        List<String> list = new ArrayList<String>(1);
        list.add(null);
        check(list.size() == 1, "size should be 1 after adding null");
        check(list.get(0) == null, "get(0) should return null");
    }

    static void testNegativeCapacityThrows() {
        try {
            new ArrayList<Integer>(-1);
            throw new RuntimeException("expected IllegalArgumentException for capacity -1");
        } catch (IllegalArgumentException e) {
            check("Illegal Capacity: -1".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testMinValueCapacityThrows() {
        try {
            new ArrayList<Integer>(Integer.MIN_VALUE);
            throw new RuntimeException("expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check(e.getMessage() != null && e.getMessage().startsWith("Illegal Capacity: "),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
