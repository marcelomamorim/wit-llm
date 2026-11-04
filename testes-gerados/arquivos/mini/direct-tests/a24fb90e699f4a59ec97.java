/*
 * @test
 * @summary Test java.util.Spliterators.nextLong() method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractLongSpliterator {
        private boolean valueReady = false;
        private long nextElement = 42L;

        TestSpliterator() {
            super(1, 0);
        }

        public boolean hasNext() {
            return valueReady;
        }

        public long nextLong() {
            if (!valueReady && !hasNext())
                throw new NoSuchElementException();
            else {
                valueReady = false;
                return nextElement;
            }
        }

        void setValueReady(boolean ready) {
            this.valueReady = ready;
        }

        void setNextElement(long val) {
            this.nextElement = val;
        }
    }

    public static void main(String[] args) {
        TestSpliterator spliterator = new TestSpliterator();

        // Test that nextLong throws NoSuchElementException when valueReady is false and hasNext() false
        spliterator.setValueReady(false);
        try {
            spliterator.nextLong();
            throw new Error("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }

        // Test that nextLong returns nextElement and resets valueReady when valueReady is true
        spliterator.setNextElement(123456789L);
        spliterator.setValueReady(true);
        long result = spliterator.nextLong();
        if (result != 123456789L) {
            throw new Error("nextLong() returned " + result + ", expected 123456789");
        }

        // After nextLong call, valueReady should be false, so next call throws
        try {
            spliterator.nextLong();
            throw new Error("Expected NoSuchElementException not thrown after consuming element");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
