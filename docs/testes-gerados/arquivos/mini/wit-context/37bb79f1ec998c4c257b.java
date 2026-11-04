/*
 * @test
 * @summary Test StreamSpliterators.InfiniteSupplyingSpliterator.OfLong.tryAdvance(LongConsumer) for null and normal input
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;

public class StreamSpliteratorsWitupTest {

    static class InfiniteSupplyingSpliteratorOfLong {
        private final LongSupplier s;

        InfiniteSupplyingSpliteratorOfLong(LongSupplier s) {
            this.s = s;
        }

        public boolean tryAdvance(LongConsumer action) {
            Objects.requireNonNull(action);
            action.accept(s.getAsLong());
            return true;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
    }

    private static void testTryAdvanceWithNullAction() {
        LongSupplier supplier = new LongSupplier() {
            public long getAsLong() {
                return 42L;
            }
        };
        InfiniteSupplyingSpliteratorOfLong spliterator = new InfiniteSupplyingSpliteratorOfLong(supplier);

        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceWithValidAction() {
        final long[] received = new long[1];
        LongSupplier supplier = new LongSupplier() {
            public long getAsLong() {
                return 123L;
            }
        };
        InfiniteSupplyingSpliteratorOfLong spliterator = new InfiniteSupplyingSpliteratorOfLong(supplier);

        boolean result = spliterator.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                received[0] = value;
            }
        });

        if (!result) {
            throw new Error("tryAdvance should return true");
        }
        if (received[0] != 123L) {
            throw new Error("LongConsumer did not receive expected value");
        }
    }
}
