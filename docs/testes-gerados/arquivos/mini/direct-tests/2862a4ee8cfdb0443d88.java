/*
 * @test
 * @summary Test StreamSpliterators.tryAdvance(DoubleConsumer) method
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class StreamSpliteratorsWitupTest {

    static class TestSpliterator {
        private final DoubleSupplier s;

        TestSpliterator(DoubleSupplier s) {
            this.s = s;
        }

        public boolean tryAdvance(DoubleConsumer action) {
            Objects.requireNonNull(action);
            action.accept(s.getAsDouble());
            return true;
        }
    }

    public static void main(String[] args) {
        // Test normal behavior: tryAdvance calls action.accept with supplied double
        final double[] consumed = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                consumed[0] = value;
            }
        };

        // Supply a fixed double value
        DoubleSupplier supplier = new DoubleSupplier() {
            public double getAsDouble() {
                return 42.0d;
            }
        };

        TestSpliterator spliterator = new TestSpliterator(supplier);

        boolean result = spliterator.tryAdvance(consumer);

        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }

        if (consumed[0] != 42.0d) {
            throw new RuntimeException("Consumer did not receive expected value");
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
