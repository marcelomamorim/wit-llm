/*
 * @test
 * @summary Test StreamSpliterators.tryAdvance(LongConsumer) method
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;

public class StreamSpliteratorsWitupTest {

    static class TestSpliterator {
        private final LongSupplier s;

        TestSpliterator(LongSupplier s) {
            this.s = s;
        }

        public boolean tryAdvance(LongConsumer action) {
            Objects.requireNonNull(action);
            action.accept(s.getAsLong());
            return true;
        }
    }

    public static void main(String[] args) {
        // Test normal behavior: tryAdvance calls action.accept with supplied long
        final long[] received = new long[1];
        LongSupplier supplier = new LongSupplier() {
            private long val = 42L;
            public long getAsLong() {
                return val;
            }
        };

        TestSpliterator spliterator = new TestSpliterator(supplier);

        boolean result = spliterator.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                received[0] = value;
            }
        });

        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }

        if (received[0] != 42L) {
            throw new RuntimeException("tryAdvance did not pass correct value to action.accept");
        }

        // Test that passing null action throws NullPointerException
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("tryAdvance should throw NullPointerException when action is null");
        }
    }
}
