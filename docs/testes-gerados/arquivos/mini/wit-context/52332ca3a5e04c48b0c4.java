/*
 * @test
 * @summary Test IntStream.iterate method for normal and exceptional behavior
 * @run main IntStreamWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class IntStreamWitupTest {
    public static void main(String[] args) {
        testIterateWithValidFunction();
        testIterateWithNullFunction();
    }

    private static void testIterateWithValidFunction() {
        final int seed = 1;
        final IntUnaryOperator f = new IntUnaryOperator() {
            private int count = 0;
            @Override
            public int applyAsInt(int operand) {
                count++;
                return operand + 1;
            }
        };

        IntStream stream = IntStream.iterate(seed, f);

        // Collect first 5 elements to verify iteration
        final int[] results = new int[5];
        final int[] index = {0};
        stream.limit(5).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                results[index[0]++] = value;
            }
        });

        // Check that the sequence starts at seed and increments by 1
        for (int i = 0; i < 5; i++) {
            if (results[i] != seed + i) {
                throw new AssertionError("Expected " + (seed + i) + " but got " + results[i]);
            }
        }
    }

    private static void testIterateWithNullFunction() {
        try {
            IntStream.iterate(0, null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
