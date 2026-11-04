/* @test    @summary Test addAll(Collection<? extends E>) method of AbstractList for normal and exceptional behavior    @run main AbstractListWitupTest */

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

public class AbstractListWitupTest {

    public static void main(String[] args) {
        testAddAllNormal();
        testAddAllIndexOutOfBounds();
    }

    private static void testAddAllNormal() {
        AbstractList<Integer> list = new AbstractList<Integer>() {
            private final java.util.ArrayList<Integer> internal = new java.util.ArrayList<>();
            @Override
            public Integer get(int index) {
                return internal.get(index);
            }
            @Override
            public int size() {
                return internal.size();
            }
            @Override
            public boolean add(Integer e) {
                return internal.add(e);
            }
            @Override
            public boolean addAll(int index, java.util.Collection<? extends Integer> c) {
                internal.addAll(index, c);
                return true;
            }
        };
        list.addAll(Arrays.asList(1, 2, 3));
        boolean result = list.addAll(Arrays.asList(4, 5));
        if (!result) {
            throw new AssertionError("addAll should return true");
        }
        if (list.size() != 5) {
            throw new AssertionError("List size should be 5, but was " + list.size());
        }
        if (list.get(4) != 5) {
            throw new AssertionError("Last element should be 5, but was " + list.get(4));
        }
    }

    private static void testAddAllIndexOutOfBounds() {
        AbstractList<Integer> list = new AbstractList<Integer>() {
            private final java.util.ArrayList<Integer> internal = new java.util.ArrayList<>();
            @Override
            public Integer get(int index) {
                return internal.get(index);
            }
            @Override
            public int size() {
                return internal.size();
            }
            @Override
            public boolean add(Integer e) {
                return internal.add(e);
            }
            @Override
            public boolean addAll(int index, java.util.Collection<? extends Integer> c) {
                if (index < 0 || index > internal.size()) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + internal.size());
                }
                internal.addAll(index, c);
                return true;
            }
        };
        list.addAll(Arrays.asList(1, 2, 3));
        try {
            list.addAll(10, Arrays.asList(4, 5));
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            list.addAll(-1, Arrays.asList(4, 5));
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
