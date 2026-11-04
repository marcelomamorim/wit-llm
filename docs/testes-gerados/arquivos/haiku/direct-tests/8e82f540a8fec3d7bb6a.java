/*
 * @test
 * @summary Verifies Iterator.next() returned by Spliterators.iterator(...) yields elements in order and throws NoSuchElementException when exhausted
 * @run main SpliteratorsWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        String[] data = {"a", "b", "c"};
        Spliterator<String> sp = Spliterators.spliterator(data, 0);
        Iterator<String> it = Spliterators.iterator(sp);

        if (!it.hasNext()) {
            throw new RuntimeException("expected hasNext() true at start");
        }
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("expected 'a' but was " + first);
        }
        // next() without a preceding hasNext() call must still advance correctly
        String second = it.next();
        if (!"b".equals(second)) {
            throw new RuntimeException("expected 'b' but was " + second);
        }
        if (!it.hasNext()) {
            throw new RuntimeException("expected hasNext() true before last element");
        }
        String third = it.next();
        if (!"c".equals(third)) {
            throw new RuntimeException("expected 'c' but was " + third);
        }
        if (it.hasNext()) {
            throw new RuntimeException("expected hasNext() false after exhaustion");
        }

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NoSuchElementException from next() on exhausted iterator");
        }

        Iterator<Object> empty = Spliterators.iterator(Spliterators.emptySpliterator());
        thrown = false;
        try {
            empty.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NoSuchElementException from next() on empty iterator");
        }
    }
}
