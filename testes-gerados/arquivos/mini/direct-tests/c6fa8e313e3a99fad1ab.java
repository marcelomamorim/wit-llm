/*
 * @test
 * @summary Test IntPipeline.forEachOrdered with simple IntConsumer
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedSum();
        testForEachOrderedEmpty();
        testForEachOrderedSideEffect();
    }

    static void testForEachOrderedSum() {
        final int[] sum = new int[1];
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sum[0] += value;
            }
        };

        IntStream stream = IntStream.of(1, 2, 3, 4);
        stream.forEachOrdered(action);

        if (sum[0] != 10) {
            throw new AssertionError("Sum should be 10 but was " + sum[0]);
        }
    }

    static void testForEachOrderedEmpty() {
        final int[] count = new int[1];
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                count[0]++;
            }
        };

        IntStream emptyStream = IntStream.empty();
        emptyStream.forEachOrdered(action);

        if (count[0] != 0) {
            throw new AssertionError("Count should be 0 for empty stream but was " + count[0]);
        }
    }

    static void testForEachOrderedSideEffect() {
        final StringBuilder sb = new StringBuilder();
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sb.append(value).append(",");
            }
        };

        IntStream stream = IntStream.of(5, 6, 7);
        stream.forEachOrdered(action);

        String result = sb.toString();
        if (!result.equals("5,6,7,")) {
            throw new AssertionError("Expected '5,6,7,' but got '" + result + "'");
        }
    }
}
