/*
 * @test
 * @summary Test java.util.Collections.CheckedCollection.forEach method for null action and normal behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static class CheckedCollection<E> implements Collection<E> {
        private final Collection<E> c;

        CheckedCollection(Collection<E> c) {
            if (c == null) throw new NullPointerException();
            this.c = c;
        }

        public void forEach(Consumer<? super E> action) {
            if (action == null) throw new NullPointerException();
            c.forEach(action);
        }

        // Minimal Collection methods to compile
        public int size() { return c.size(); }
        public boolean isEmpty() { return c.isEmpty(); }
        public boolean contains(Object o) { return c.contains(o); }
        public java.util.Iterator<E> iterator() { return c.iterator(); }
        public Object[] toArray() { return c.toArray(); }
        public <T> T[] toArray(T[] a) { return c.toArray(a); }
        public boolean add(E e) { return c.add(e); }
        public boolean remove(Object o) { return c.remove(o); }
        public boolean containsAll(Collection<?> coll) { return c.containsAll(coll); }
        public boolean addAll(Collection<? extends E> coll) { return c.addAll(coll); }
        public boolean removeAll(Collection<?> coll) { return c.removeAll(coll); }
        public boolean retainAll(Collection<?> coll) { return c.retainAll(coll); }
        public void clear() { c.clear(); }
    }

    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
        System.out.println("All tests passed.");
    }

    private static void testForEachNullAction() {
        Collection<String> base = new ArrayList<>();
        base.add("one");
        CheckedCollection<String> checked = new CheckedCollection<>(base);
        try {
            checked.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachNormal() {
        Collection<String> base = new ArrayList<>();
        base.add("a");
        base.add("b");
        final StringBuilder sb = new StringBuilder();
        CheckedCollection<String> checked = new CheckedCollection<>(base);
        checked.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        String result = sb.toString();
        if (!"ab".equals(result) && !"ba".equals(result)) {
            throw new RuntimeException("Unexpected forEach iteration result: " + result);
        }
    }
}
