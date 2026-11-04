/*
 * @test
 * @summary Tests observable behavior of ArrayList.subList(int, int), including view semantics, range validation and fail-fast behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListContentAndSize();
        testSubListIsView();
        testEmptyAndFullSubList();
        testInvalidFromIndexNegative();
        testInvalidToIndexTooLarge();
        testFromIndexGreaterThanToIndex();
        testStructuralModificationDetected();
    }

    static ArrayList<String> newList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("e" + i);
        }
        return list;
    }

    static void testSubListContentAndSize() {
        ArrayList<String> list = newList();
        List<String> sub = list.subList(1, 3);
        check(sub.size() == 2, "size of subList(1,3) should be 2");
        check("e1".equals(sub.get(0)), "sub.get(0) should be e1");
        check("e2".equals(sub.get(1)), "sub.get(1) should be e2");
    }

    static void testSubListIsView() {
        ArrayList<String> list = newList();
        List<String> sub = list.subList(1, 3);
        sub.set(0, "x");
        check("x".equals(list.get(1)), "set on subList should write through to backing list");
        check(list.size() == 5, "backing list size should be unchanged by set");
    }

    static void testEmptyAndFullSubList() {
        ArrayList<String> list = newList();
        check(list.subList(2, 2).isEmpty(), "subList(2,2) should be empty");
        check(list.subList(0, 5).size() == 5, "subList(0,size) should contain all elements");
        check(list.subList(0, 0).isEmpty(), "subList(0,0) should be empty");
    }

    static void testInvalidFromIndexNegative() {
        ArrayList<String> list = newList();
        boolean thrown = false;
        try {
            list.subList(-1, 2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "subList(-1,2) should throw IndexOutOfBoundsException");
    }

    static void testInvalidToIndexTooLarge() {
        ArrayList<String> list = newList();
        boolean thrown = false;
        try {
            list.subList(0, 6);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "subList(0,6) on size 5 should throw IndexOutOfBoundsException");
    }

    static void testFromIndexGreaterThanToIndex() {
        ArrayList<String> list = newList();
        boolean thrown = false;
        try {
            list.subList(3, 2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "subList(3,2) should throw IllegalArgumentException");
    }

    static void testStructuralModificationDetected() {
        ArrayList<String> list = newList();
        List<String> sub = list.subList(0, 2);
        list.add("z");
        boolean thrown = false;
        try {
            sub.size();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList should detect structural modification of backing list");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
