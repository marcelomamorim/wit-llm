/*
 * @test
 * @summary Verify that peek's action observes each double element before downstream without altering elements
 * @run main DoublePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testPeekObservesAllElementsInOrder();
        testPeekDoesNotAlterElements();
        testPeekRunsBeforeDownstreamPerElement();
    }

    static void testPeekObservesAllElementsInOrder() {
        List<Double> seen = new ArrayList<>();
        double[] result = DoubleStream.of(1.5, -2.0, 3.25)
                .peek(d -> seen.add(d))
                .toArray();
        check(Arrays.asList(1.5, -2.0, 3.25).equals(seen),
              "peek action saw " + seen);
        check(Arrays.equals(new double[]{1.5, -2.0, 3.25}, result),
              "peek altered result: " + Arrays.toString(result));
    }

    static void testPeekDoesNotAlterElements() {
        double sum = DoubleStream.of(1.0, 2.0, 3.0)
                .peek(d -> { })
                .sum();
        check(sum == 6.0, "sum after peek was " + sum);

        long count = DoubleStream.of(0.0, 0.0)
                .peek(d -> { })
                .count();
        check(count == 2L, "count after peek was " + count);
    }

    static void testPeekRunsBeforeDownstreamPerElement() {
        List<String> log = new ArrayList<>();
        DoubleStream.of(1.0, 2.0)
                .peek(d -> log.add("p" + d))
                .forEach(d -> log.add("f" + d));
        List<String> expected = Arrays.asList("p1.0", "f1.0", "p2.0", "f2.0");
        check(expected.equals(log), "interleaving was " + log);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
