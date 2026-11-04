/*
 * @test
 * @summary Tests for java.util.AbstractList.subList(int, int)
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.List;

public class AbstractListWitupTest {

    public static void main(String[] args) {
        testSubListValidRange();
        testSubListFromIndexNegative();
        testSubListToIndexGreaterThanSize();
        testSubListFromIndexGreaterThanToIndex();
    }

    static void testSubListValidRange() {
        AbstractList<String> list = new AbstractList<String>() {
            @Override
            public String get(int index) {
                return "item" + index;
            }

            @Override
            public int size() {
                return 5;
            }
        };
        List<String> sublist = list.subList(1, 4);
        if (sublist.size() != 3) {
            throw new AssertionError("Expected sublist size 3, got " + sublist.size());
        }
        if (!sublist.get(0).equals("item1")) {
            throw new AssertionError("Expected 'item1', got " + sublist.get(0));
        }
        if (!sublist.get(2).equals("item3")) {
            throw new AssertionError("Expected 'item3', got " + sublist.get(2));
        }
    }

    static void testSubListFromIndexNegative() {
        AbstractList<String> list = new AbstractList<String>() {
            @Override
            public String get(int index) {
                return "item" + index;
            }

            @Override
            public int size() {
                return 5;
            }
        };
        try {
            list.subList(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListToIndexGreaterThanSize() {
        AbstractList<String> list = new AbstractList<String>() {
            @Override
            public String get(int index) {
                return "item" + index;
            }

            @Override
            public int size() {
                return 5;
            }
        };
        try {
            list.subList(1, 6);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex 6");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListFromIndexGreaterThanToIndex() {
        AbstractList<String> list = new AbstractList<String>() {
            @Override
            public String get(int index) {
                return "item" + index;
            }

            @Override
            public int size() {
                return 5;
            }
        };
        try {
            list.subList(3, 2);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
