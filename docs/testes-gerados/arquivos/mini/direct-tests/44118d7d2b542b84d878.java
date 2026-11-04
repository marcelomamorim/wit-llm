/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(DoubleConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingDelegation();
    }

    static void testForEachRemainingNullAction() {
        Spliterators.AbstractDoubleSpliterator spliterator = new Spliterators.AbstractDoubleSpliterator(1, 0) {
            private boolean done = false;
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                if (done) return false;
                done = true;
                action.accept(42.0);
                return true;
            }
        };

        boolean npeThrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachRemainingDelegation() {
        final double[] seen = new double[10];
        final int[] count = new int[1];

        Spliterators.AbstractDoubleSpliterator spliterator = new Spliterators.AbstractDoubleSpliterator(3, 0) {
            private int index = 0;
            private final double[] values = {1.0, 2.0, 3.0};

            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                if (index >= values.length) return false;
                action.accept(values[index++]);
                return true;
            }
        };

        spliterator.forEachRemaining(new DoubleConsumer() {
            public void accept(double value) {
                seen[count[0]++] = value;
            }
        });

        if (count[0] != 3) {
            throw new AssertionError("Expected 3 elements consumed, got " + count[0]);
        }
        if (seen[0] != 1.0 || seen[1] != 2.0 || seen[2] != 3.0) {
            throw new AssertionError("Elements consumed do not match expected values");
        }
    }
}
