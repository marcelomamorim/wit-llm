/*
 * @test
 * @summary Test AbstractList addAll(Collection) method
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class AbstractListWitupTest {

    static class SimpleList<E> extends AbstractList<E> {
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
        SimpleList<String> list = new SimpleList<>();

        // Initially empty list
        if (list.size() != 0) {
            throw new RuntimeException("Initial list size should be 0");
        }

        // AddAll with empty collection returns false and list remains empty
        boolean resultEmpty = list.addAll(new ArrayList<String>());
        if (resultEmpty) {
            throw new RuntimeException("addAll with empty collection should return false");
        }
        if (list.size() != 0) {
            throw new RuntimeException("List size should remain 0 after addAll with empty collection");
        }

        // AddAll with non-empty collection returns true and elements are added
        Collection<String> toAdd = Arrays.asList("a", "b", "c");
        boolean result = list.addAll(toAdd);
        if (!result) {
            throw new RuntimeException("addAll with non-empty collection should return true");
        }
        if (list.size() != 3) {
            throw new RuntimeException("List size should be 3 after addAll");
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1)) || !"c".equals(list.get(2))) {
            throw new RuntimeException("List elements do not match after addAll");
        }

        // AddAll again to append more elements
        Collection<String> more = Arrays.asList("d", "e");
        boolean result2 = list.addAll(more);
        if (!result2) {
            throw new RuntimeException("addAll with non-empty collection should return true");
        }
        if (list.size() != 5) {
            throw new RuntimeException("List size should be 5 after second addAll");
        }
        if (!"d".equals(list.get(3)) || !"e".equals(list.get(4))) {
            throw new RuntimeException("List elements do not match after second addAll");
        }
    }
}
