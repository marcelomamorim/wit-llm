/*
 * @test
 * @summary Test addAll(Collection<? extends E> c) method of AbstractList
 * @run main AbstractListWitupTest
 */

import java.util.*;

public class AbstractListWitupTest {

    public static void main(String[] args) {
        testAddAllWithEmptyList();
        testAddAllWithNonEmptyList();
        testAddAllWithNullCollection();
    }

    static void testAddAllWithEmptyList() {
        List<Integer> list = new ArrayList<>();
        List<Integer> toAdd = Arrays.asList(1, 2, 3);
        AbstractList<Integer> abstractList = new AbstractList<Integer>() {
            private final List<Integer> internalList = list;
            @Override
            public Integer get(int index) {
                return internalList.get(index);
            }
            @Override
            public int size() {
                return internalList.size();
            }
            @Override
            public boolean addAll(Collection<? extends Integer> c) {
                return super.addAll(c);
            }
            @Override
            public boolean addAll(int index, Collection<? extends Integer> c) {
                return internalList.addAll(index, c);
            }
        };
        boolean result = abstractList.addAll(toAdd);
        assert result : "addAll should return true when list is modified";
        assert abstractList.size() == 3 : "List size should be 3 after addAll";
        assert abstractList.get(0) == 1 : "First element should be 1";
        assert abstractList.get(2) == 3 : "Third element should be 3";
    }

    static void testAddAllWithNonEmptyList() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> toAdd = Arrays.asList("c", "d");
        AbstractList<String> abstractList = new AbstractList<String>() {
            private final List<String> internalList = list;
            @Override
            public String get(int index) {
                return internalList.get(index);
            }
            @Override
            public int size() {
                return internalList.size();
            }
            @Override
            public boolean addAll(int index, Collection<? extends String> c) {
                return internalList.addAll(index, c);
            }
        };
        boolean result = abstractList.addAll(toAdd);
        assert result : "addAll should return true when list is modified";
        assert abstractList.size() == 4 : "List size should be 4 after addAll";
        assert abstractList.get(2).equals("c") : "Third element should be 'c'";
        assert abstractList.get(3).equals("d") : "Fourth element should be 'd'";
    }

    static void testAddAllWithNullCollection() {
        List<Integer> list = new ArrayList<>();
        AbstractList<Integer> abstractList = new AbstractList<Integer>() {
            private final List<Integer> internalList = list;
            @Override
            public Integer get(int index) {
                return internalList.get(index);
            }
            @Override
            public int size() {
                return internalList.size();
            }
        };
        try {
            abstractList.addAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
