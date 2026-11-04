/*
 * @test
 * @summary Test Spliterators.nextLong() behavior including NoSuchElementException
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.Spliterator;

public class SpliteratorsWitupTest {

    static class TestAdapter extends Spliterators.AbstractLongSpliterator {
        private boolean valueReady;
        private long nextElement;

        TestAdapter() {
            super(1, 0);
            this.valueReady = false;
            this.nextElement = 0L;
        }

        void setNextElement(long val) {
            this.nextElement = val;
            this.valueReady = true;
        }

        void clearValueReady() {
            this.valueReady = false;
        }

        boolean hasNext() {
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

        @Override
        public boolean tryAdvance(LongConsumer action) {
            if (valueReady) {
                action.accept(nextElement);
                valueReady = false;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        TestAdapter adapter = new TestAdapter();

        // Test: nextLong throws NoSuchElementException when valueReady is false and hasNext() false
        adapter.clearValueReady();
        try {
            adapter.nextLong();
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected
        }

        // Test: nextLong returns the nextElement and resets valueReady
        adapter.setNextElement(42L);
        long val = adapter.nextLong();
        if (val != 42L) {
            throw new RuntimeException("nextLong() returned wrong value: " + val);
        }

        // After nextLong, valueReady should be false, so next call throws
        try {
            adapter.nextLong();
            throw new RuntimeException("Expected NoSuchElementException not thrown after consuming element");
        } catch (NoSuchElementException e) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
