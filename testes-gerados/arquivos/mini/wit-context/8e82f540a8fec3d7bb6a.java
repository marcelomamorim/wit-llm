/*
 * @test
 * @summary Test java.util.Spliterators.next() method behavior including NoSuchElementException
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestAdapter<T> extends Spliterators.AbstractSpliterator<T> {
        boolean valueReady;
        T nextElement;

        TestAdapter() {
            super(Long.MAX_VALUE, 0);
            this.valueReady = false;
            this.nextElement = null;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super T> action) {
            if (valueReady) {
                action.accept(nextElement);
                valueReady = false;
                return true;
            }
            return false;
        }

        public T next() {
            if (!valueReady && !tryAdvance(e -> nextElement = e))
                throw new NoSuchElementException();
            else {
                valueReady = false;
                return nextElement;
            }
        }

        void setNextElement(T element) {
            this.nextElement = element;
            this.valueReady = true;
        }
    }

    public static void main(String[] args) {
        testNextReturnsElementWhenReady();
        testNextThrowsWhenNotReadyAndNoNext();
    }

    private static void testNextReturnsElementWhenReady() {
        TestAdapter<String> adapter = new TestAdapter<>();
        adapter.setNextElement("test");
        String result = adapter.next();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }
    }

    private static void testNextThrowsWhenNotReadyAndNoNext() {
        TestAdapter<String> adapter = new TestAdapter<>();
        boolean thrown = false;
        try {
            adapter.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        }
    }
}
