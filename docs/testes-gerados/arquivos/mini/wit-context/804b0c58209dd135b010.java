/*
 * @test
 * @summary Test Stream.iterate(T, UnaryOperator<T>) for null checks and basic iteration
 * @run main StreamWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamWitupTest {
    public static void main(String[] args) {
        testNullUnaryOperator();
        testBasicIteration();
    }

    private static void testNullUnaryOperator() {
        try {
            Stream.iterate("seed", null);
            throw new Error("Expected NullPointerException for null UnaryOperator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testBasicIteration() {
        final int limit = 5;
        Stream<Integer> stream = Stream.iterate(1, new UnaryOperator<Integer>() {
            public Integer apply(Integer i) {
                return i + 1;
            }
        });

        // Collect first 'limit' elements to verify iteration
        int[] results = new int[limit];
        final int[] index = {0};
        stream.limit(limit).forEach(new Consumer<Integer>() {
            public void accept(Integer i) {
                results[index[0]++] = i;
            }
        });

        // Check that results are sequential starting from seed
        for (int i = 0; i < limit; i++) {
            if (results[i] != i + 1) {
                throw new Error("Iteration produced unexpected value at index " + i + ": " + results[i]);
            }
        }
    }
}
