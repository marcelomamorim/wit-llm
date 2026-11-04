/*
 * @test
 * @summary Test AbstractList.subList(int, int) method behavior
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

public class AbstractListWitupTest {

    static class SimpleList extends AbstractList<Integer> {
        private final Integer[] data;

        SimpleList(Integer[] data) {
            this.data = data;
        }

        @Override
        public Integer get(int index) {
            return data[index];
        }

        @Override
        public int size() {
            return data.length;
        }
    }

    public static void main(String[] args) {
        testSubListBasic();
        testSubListEmpty();
        testSubListFullRange();
        testSubListInvalidRange();
    }

    private static void testSubListBasic() {
        Integer[] arr = {10, 20, 30, 40, 50};
        SimpleList list = new SimpleList(arr);
        List<Integer> sub = list.subList(1, 4);

        if (sub.size() != 3) {
            throw new RuntimeException("subList size expected 3 but was " + sub.size());
        }
        if (!sub.get(0).equals(20) || !sub.get(2).equals(40)) {
            throw new RuntimeException("subList elements incorrect");
        }
        if (!(sub instanceof RandomAccess)) {
            throw new RuntimeException("subList should implement RandomAccess");
        }
    }

    private static void testSubListEmpty() {
        Integer[] arr = {1, 2, 3};
        SimpleList list = new SimpleList(arr);
        List<Integer> sub = list.subList(2, 2);

        if (sub.size() != 0) {
            throw new RuntimeException("Empty subList size expected 0 but was " + sub.size());
        }
    }

    private static void testSubListFullRange() {
        Integer[] arr = {5, 6, 7};
        SimpleList list = new SimpleList(arr);
        List<Integer> sub = list.subList(0, arr.length);

        if (sub.size() != arr.length) {
            throw new RuntimeException("Full range subList size expected " + arr.length + " but was " + sub.size());
        }
        for (int i = 0; i < arr.length; i++) {
            if (!sub.get(i).equals(arr[i])) {
                throw new RuntimeException("Full range subList element mismatch at index " + i);
            }
        }
    }

    private static void testSubListInvalidRange() {
        Integer[] arr = {1, 2, 3};
        SimpleList list = new SimpleList(arr);

        boolean caught = false;
        try {
            list.subList(-1, 2);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative fromIndex");
        }

        caught = false;
        try {
            list.subList(1, 5);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for toIndex > size");
        }

        caught = false;
        try {
            list.subList(2, 1);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException for fromIndex > toIndex");
        }
    }
}
