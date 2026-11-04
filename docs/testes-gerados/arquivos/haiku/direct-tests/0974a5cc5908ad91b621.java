/*
 * @test
 * @summary Verifies observable behavior of AbstractList.subList(int,int): range view, write-through, and range-check exceptions
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

public class AbstractListWitupTest {

    static class IntList extends AbstractList<Integer> {
        private final int[] data;

        IntList(int... values) {
            data = values;
        }

        @Override
        public Integer get(int index) {
            return data[index];
        }

        @Override
        public int size() {
            return data.length;
        }

        @Override
        public Integer set(int index, Integer e) {
            Integer old = data[index];
            data[index] = e;
            return old;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRangeView() {
        IntList l = new IntList(1, 2, 3, 4);
        List<Integer> s = l.subList(1, 3);
        check(s.size() == 2, "size of sublist(1,3) should be 2");
        check(s.get(0) == 2, "sublist get(0) should be 2");
        check(s.get(1) == 3, "sublist get(1) should be 3");
    }

    static void testEmptyRange() {
        IntList l = new IntList(1, 2, 3, 4);
        check(l.subList(2, 2).isEmpty(), "subList(2,2) should be empty");
        check(l.subList(0, 0).isEmpty(), "subList(0,0) should be empty");
    }

    static void testFullRange() {
        IntList l = new IntList(1, 2, 3, 4);
        check(l.subList(0, 4).equals(Arrays.asList(1, 2, 3, 4)),
              "subList(0,size) should equal the whole list");
    }

    static void testWriteThrough() {
        IntList l = new IntList(1, 2, 3, 4);
        List<Integer> s = l.subList(1, 3);
        s.set(0, 20);
        check(l.get(1) == 20, "set on subList should write through to backing list");
        check(l.get(0) == 1 && l.get(3) == 4, "set on subList should not affect elements outside the range");
    }

    static void testFromGreaterThanTo() {
        IntList l = new IntList(1, 2, 3, 4);
        boolean thrown = false;
        try {
            l.subList(3, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "subList(3,1) should throw IllegalArgumentException");
    }

    static void testNegativeFrom() {
        IntList l = new IntList(1, 2, 3, 4);
        boolean thrown = false;
        try {
            l.subList(-1, 2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "subList(-1,2) should throw IndexOutOfBoundsException");
    }

    static void testToBeyondSize() {
        IntList l = new IntList(1, 2, 3, 4);
        boolean thrown = false;
        try {
            l.subList(0, 5);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "subList(0,5) should throw IndexOutOfBoundsException");
    }

    public static void main(String[] args) {
        testRangeView();
        testEmptyRange();
        testFullRange();
        testWriteThrough();
        testFromGreaterThanTo();
        testNegativeFrom();
        testToBeyondSize();
    }
}
