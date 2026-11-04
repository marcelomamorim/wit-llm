/*
 * @test
 * @summary Verifies Stream.reduce(identity, accumulator, combiner), which delegates to ReferencePipeline.reduce
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialSum() {
        int result = Stream.of(1, 2, 3).reduce(0, (a, b) -> a + b, Integer::sum);
        check(result == 6, "expected 6 but was " + result);
    }

    static void testEmptyStreamReturnsIdentity() {
        String result = Stream.<String>empty().reduce("seed", (a, b) -> a + b, (a, b) -> a + b);
        check("seed".equals(result), "expected seed but was " + result);
    }

    static void testSequentialConcatenationOrder() {
        String result = Stream.of("a", "b", "c").reduce("", (a, b) -> a + b, (a, b) -> a + b);
        check("abc".equals(result), "expected abc but was " + result);
    }

    static void testCombinerNotUsedSequentially() {
        int[] combinerCalls = new int[1];
        int result = Stream.of(1, 2, 3, 4).reduce(0,
                (a, b) -> a + b,
                (a, b) -> { combinerCalls[0]++; return a + b; });
        check(result == 10, "expected 10 but was " + result);
        check(combinerCalls[0] == 0, "combiner should not run sequentially, calls=" + combinerCalls[0]);
    }

    static void testParallelSumMatchesSequential() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int seq = values.stream().reduce(0, (a, b) -> a + b, Integer::sum);
        int par = values.parallelStream().reduce(0, (a, b) -> a + b, Integer::sum);
        check(seq == 55, "sequential expected 55 but was " + seq);
        check(par == 55, "parallel expected 55 but was " + par);
    }

    static void testParallelReductionOverLargeRange() {
        int sum = Stream.iterate(1, x -> x + 1).limit(1000).parallel()
                .reduce(0, (a, b) -> a + b, Integer::sum);
        check(sum == 500500, "expected 500500 but was " + sum);
    }

    static void testMappedAccumulatorTypeChange() {
        int totalLength = Stream.of("aa", "bbb", "c").reduce(0,
                (acc, s) -> acc + s.length(),
                Integer::sum);
        check(totalLength == 6, "expected 6 but was " + totalLength);
    }

    public static void main(String[] args) {
        testSequentialSum();
        testEmptyStreamReturnsIdentity();
        testSequentialConcatenationOrder();
        testCombinerNotUsedSequentially();
        testParallelSumMatchesSequential();
        testParallelReductionOverLargeRange();
        testMappedAccumulatorTypeChange();
    }
}
