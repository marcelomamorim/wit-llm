/*
 * @test
 * @summary Witup-generated checks for ArrayList.subList range validation
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNegativeFromIndex();
        testToIndexBeyondSize();
        testFromGreaterThanTo();
        testEmptyListOutOfRange();
        testValidSubList();
        testEmptySubListAtBoundary();
    }

    static void testNegativeFromIndex() {
        List<Integer> list = filled(3);
        expect(IndexOutOfBoundsException.class, list, -1, 2);
    }

    static void testToIndexBeyondSize() {
        List<Integer> list = filled(3);
        expect(IndexOutOfBoundsException.class, list, 0, 4);
    }

    static void testFromGreaterThanTo() {
        List<Integer> list = filled(3);
        expect(IllegalArgumentException.class, list, 2, 1);
    }

    static void testEmptyListOutOfRange() {
        List<Integer> list = new ArrayList<>();
        expect(IndexOutOfBoundsException.class, list, 0, 1);
    }

    static void testValidSubList() {
        List<Integer> list = filled(5);
        List<Integer> sub = list.subList(1, 3);
        check(sub.size() == 2, "unexpected size: " + sub.size());
        check(sub.get(0).intValue() == 1, "unexpected element 0");
        check(sub.get(1).intValue() == 2, "unexpected element 1");
    }

    static void testEmptySubListAtBoundary() {
        List<Integer> list = filled(3);
        check(list.subList(0, 0).isEmpty(), "subList(0,0) not empty");
        check(list.subList(3, 3).isEmpty(), "subList(3,3) not empty");
    }

    static List<Integer> filled(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return list;
    }

    static void expect(Class<?> type, List<Integer> list, int from, int to) {
        try {
            list.subList(from, to);
        } catch (RuntimeException e) {
            check(type.isInstance(e), "unexpected exception for (" + from + "," + to + "): " + e);
            return;
        }
        throw new RuntimeException("no exception for (" + from + "," + to + ")");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
