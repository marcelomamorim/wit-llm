/*
 * @test
 * @summary Test StreamSpliterators.InfiniteSupplyingSpliterator.OfDouble.tryAdvance(DoubleConsumer) for NPE and normal behavior
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class StreamSpliteratorsWitupTest {

    static class InfiniteSupplyingSpliteratorOfDouble {
        private final DoubleSupplier s;

        InfiniteSupplyingSpliteratorOfDouble(DoubleSupplier s) {
            this.s = s;
        }

        public boolean tryAdvance(DoubleConsumer action) {
            Objects.requireNonNull(action);
            action.accept(s.getAsDouble());
            return true;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        DoubleSupplier supplier = new DoubleSupplier() {
            public double getAsDouble() {
                return 1.23d;
            }
        };
        InfiniteSupplyingSpliteratorOfDouble spliterator = new InfiniteSupplyingSpliteratorOfDouble(supplier);

        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormal() {
        final double[] received = new double[1];
        DoubleSupplier supplier = new DoubleSupplier() {
            public double getAsDouble() {
                return 4.56d;
            }
        };
        InfiniteSupplyingSpliteratorOfDouble spliterator = new InfiniteSupplyingSpliteratorOfDouble(supplier);

        boolean result = spliterator.tryAdvance(new DoubleConsumer() {
            public void accept(double value) {
                received[0] = value;
            }
        });

        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (received[0] != 4.56d) {
            throw new RuntimeException("tryAdvance did not pass correct value to action");
        }
    }
}
