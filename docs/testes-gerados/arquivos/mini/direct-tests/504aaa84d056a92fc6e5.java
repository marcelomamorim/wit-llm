/*
 * @test
 * @summary Test java.util.Spliterators.nextDouble() method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractDoubleSpliterator {
        private boolean valueReady = false;
        private double nextElement = 42.0;

        TestSpliterator() {
            super(1, 0);
        }

        boolean hasNext = true;

        boolean hasNext() {
            return hasNext;
        }

        public double nextDouble() {
            if (!valueReady && !hasNext())
                throw new NoSuchElementException();
            else {
                valueReady = false;
                return nextElement;
            }
        }

        void setValueReady(boolean ready) {
            valueReady = ready;
        }

        void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }
    }

    public static void main(String[] args) {
        TestSpliterator spliterator = new TestSpliterator();

        // Case 1: valueReady true, should return nextElement
        spliterator.setValueReady(true);
        spliterator.setHasNext(false); // hasNext false but valueReady true
        double val = spliterator.nextDouble();
        if (val != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + val);
        }

        // Case 2: valueReady false, hasNext true, should throw NoSuchElementException
        spliterator.setValueReady(false);
        spliterator.setHasNext(false);
        boolean thrown = false;
        try {
            spliterator.nextDouble();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when valueReady is false and hasNext is false");
        }
    }
}
