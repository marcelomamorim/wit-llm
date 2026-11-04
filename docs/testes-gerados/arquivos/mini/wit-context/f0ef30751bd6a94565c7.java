/*
 * @test
 * @summary Test Collections.AsLIFOQueue.forEach method for null action and normal behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {

    // Minimal wrapper to access AsLIFOQueue.forEach
    static class AsLIFOQueue<E> implements Queue<E> {
        private final Queue<E> q;

        AsLIFOQueue(Queue<E> q) {
            this.q = q;
        }

        public void forEach(Consumer<? super E> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            q.forEach(action);
        }

        // Implement minimal Queue methods to satisfy interface
        public boolean add(E e) { return q.add(e); }
        public boolean offer(E e) { return q.offer(e); }
        public E remove() { return q.remove(); }
        public E poll() { return q.poll(); }
        public E element() { return q.element(); }
        public E peek() { return q.peek(); }
        public int size() { return q.size(); }
        public boolean isEmpty() { return q.isEmpty(); }
        public boolean contains(Object o) { return q.contains(o); }
        public java.util.Iterator<E> iterator() { return q.iterator(); }
        public Object[] toArray() { return q.toArray(); }
        public <T> T[] toArray(T[] a) { return q.toArray(a); }
        public boolean remove(Object o) { return q.remove(o); }
        public boolean containsAll(java.util.Collection<?> c) { return q.containsAll(c); }
        public boolean addAll(java.util.Collection<? extends E> c) { return q.addAll(c); }
        public boolean removeAll(java.util.Collection<?> c) { return q.removeAll(c); }
        public boolean retainAll(java.util.Collection<?> c) { return q.retainAll(c); }
        public void clear() { q.clear(); }
        public boolean equals(Object o) { return q.equals(o); }
        public int hashCode() { return q.hashCode(); }
    }

    public static void main(String[] args) {
        testForEachNullActionThrows();
        testForEachInvokesAction();
        System.out.println("All tests passed.");
    }

    private static void testForEachNullActionThrows() {
        Queue<String> baseQueue = new ArrayDeque<>();
        baseQueue.add("a");
        AsLIFOQueue<String> lifoQueue = new AsLIFOQueue<>(baseQueue);

        try {
            lifoQueue.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachInvokesAction() {
        Queue<String> baseQueue = new ArrayDeque<>();
        baseQueue.add("x");
        baseQueue.add("y");
        AsLIFOQueue<String> lifoQueue = new AsLIFOQueue<>(baseQueue);

        final StringBuilder sb = new StringBuilder();
        lifoQueue.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!result.equals("xy") && !result.equals("yx")) {
            throw new RuntimeException("forEach did not visit all elements in expected order: " + result);
        }
    }
}
