/*
 * @test
 * @summary Tests ArrayList.set return value, null handling, index bounds checks and comodification detection through subList.set
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetReturnsOldValue();
        testSetAcceptsNull();
        testSetIndexOutOfBounds();
        testSetOnEmptyListThrows();
        testSubListSetComodification();
        testSubListSetIndexOutOfBounds();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetReturnsOldValue() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String old = list.set(1, "x");
        check("b".equals(old), "set must return the previous element");
        check("x".equals(list.get(1)), "set must store the new element");
        check(list.size() == 3, "set must not change size");
        check("a".equals(list.get(0)), "set must not change other elements");
        check("c".equals(list.get(2)), "set must not change other elements");
    }

    static void testSetAcceptsNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        String old = list.set(0, null);
        check("a".equals(old), "set must return the previous element");
        check(list.get(0) == null, "set must allow storing null");
        check(list.size() == 1, "size must remain unchanged");
    }

    static void testSetIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int[] invalid = {-1, 2, 5};
        for (int index : invalid) {
            boolean thrown = false;
            try {
                list.set(index, "z");
            } catch (IndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "set must throw IndexOutOfBoundsException for index " + index);
        }
        check(list.size() == 2, "failed set must not change size");
        check("a".equals(list.get(0)), "failed set must not change element 0");
        check("b".equals(list.get(1)), "failed set must not change element 1");
    }

    static void testSetOnEmptyListThrows() {
        ArrayList<String> list = new ArrayList<>();
        boolean thrown = false;
        try {
            list.set(0, "x");
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set on empty list must throw IndexOutOfBoundsException");
        check(list.isEmpty(), "failed set must not add elements");
    }

    static void testSubListSetComodification() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sub = list.subList(0, 2);
        list.add(4);
        boolean thrown = false;
        try {
            sub.set(0, 9);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList.set must detect structural modification of the root list");
    }

    static void testSubListSetIndexOutOfBounds() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sub = list.subList(0, 2);
        boolean thrown = false;
        try {
            sub.set(5, 9);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "subList.set must throw IndexOutOfBoundsException for invalid index");
        check(list.get(0) == 1 && list.get(1) == 2, "failed subList.set must not modify the list");
    }
}
