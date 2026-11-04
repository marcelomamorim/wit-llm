/*
 * @test
 * @summary Test java.util.AbstractList.addAll(Collection<? extends E>) method
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class AbstractListWitupTest {

    static class SimpleAbstractList<E> extends AbstractList<E> {
        private final ArrayList<E> data = new ArrayList<>();

        @Override
        public E get(int index) {
            return data.get(index);
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public void add(int index, E element) {
            data.add(index, element);
        }

        @Override
        public E set(int index, E element) {
            return data.set(index, element);
        }

        @Override
        public E remove(int index) {
            return data.remove(index);
        }
    }

    public static void main(String[] args) {
        testAddAllWithValidCollection();
        testAddAllWithEmptyCollection();
        testAddAllWithNullCollection();
    }

    private static void testAddAllWithValidCollection() {
        SimpleAbstractList<String> list = new SimpleAbstractList<>();
        list.add(0, "a");
        list.add(1, "b");

        Collection<String> toAdd = new ArrayList<>();
        toAdd.add("c");
        toAdd.add("d");

        boolean result = list.addAll(toAdd);

        if (!result) {
            throw new RuntimeException("addAll should return true when collection is non-empty");
        }

        if (list.size() != 4) {
            throw new RuntimeException("List size should be 4 after addAll");
        }

        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1)) || !"c".equals(list.get(2)) || !"d".equals(list.get(3))) {
            throw new RuntimeException("List elements are not as expected after addAll");
        }
    }

    private static void testAddAllWithEmptyCollection() {
        SimpleAbstractList<Integer> list = new SimpleAbstractList<>();
        list.add(0, 1);
        list.add(1, 2);

        Collection<Integer> empty = Collections.emptyList();

        boolean result = list.addAll(empty);

        if (result) {
            throw new RuntimeException("addAll should return false when collection is empty");
        }

        if (list.size() != 2) {
            throw new RuntimeException("List size should remain 2 after addAll with empty collection");
        }

        if (list.get(0) != 1 || list.get(1) != 2) {
            throw new RuntimeException("List elements changed unexpectedly after addAll with empty collection");
        }
    }

    private static void testAddAllWithNullCollection() {
        SimpleAbstractList<Object> list = new SimpleAbstractList<>();
        try {
            list.addAll(null);
            throw new RuntimeException("addAll should throw NullPointerException when collection is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
