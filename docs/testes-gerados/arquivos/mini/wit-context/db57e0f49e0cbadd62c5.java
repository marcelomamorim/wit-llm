/*
 * @test
 * @summary Test DoubleStream.iterate method for null argument and basic iteration
 * @run main DoubleStreamWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testIterateNullFunction();
        testIterateBasicSequence();
    }

    private static void testIterateNullFunction() {
        try {
            DoubleStream.iterate(0.0, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testIterateBasicSequence() {
        final double seed = 1.0;
        DoubleUnaryOperator f = new DoubleUnaryOperator() {
            public double applyAsDouble(double operand) {
                return operand + 1.0;
            }
        };

        DoubleStream ds = DoubleStream.iterate(seed, f);

        // Collect first 5 elements and verify sequence
        final double[] results = new double[5];
        final int[] index = {0};
        ds.limit(5).forEach(new DoubleConsumer() {
            public void accept(double value) {
                results[index[0]++] = value;
            }
        });

        for (int i = 0; i < 5; i++) {
            double expected = seed + i;
            if (results[i] != expected) {
                throw new RuntimeException("Element at index " + i + " expected: " + expected + ", but was: " + results[i]);
            }
        }
    }
}
