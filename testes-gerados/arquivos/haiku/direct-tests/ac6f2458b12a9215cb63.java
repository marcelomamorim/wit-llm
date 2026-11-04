/*
 * @test
 * @summary Verify IntPipeline.forEachOrdered through the public IntStream API in sequential and parallel modes
 * @run main IntPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialOrderAndElements();
        testParallelOrderAndElements();
        testEmptySequentialStream();
        testEmptyParallelStream();
        testSequentialSum();
        testParallelSumIsComplete();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialOrderAndElements() {
        List<Integer> seen = new ArrayList<>();
        IntStream.range(0, 5).forEachOrdered(seen::add);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expected.add(i);
        }
        check(seen.equals(expected), "sequential forEachOrdered must visit elements in encounter order");
    }

    static void testParallelOrderAndElements() {
        List<Integer> seen = new ArrayList<>();
        IntStream.range(0, 1000).parallel().forEachOrdered(seen::add);
        check(seen.size() == 1000, "parallel forEachOrdered must visit every element");
        for (int i = 0; i < 1000; i++) {
            check(seen.get(i) == i, "parallel forEachOrdered must preserve encounter order at index " + i);
        }
    }

    static void testEmptySequentialStream() {
        int[] calls = new int[1];
        IntStream.empty().forEachOrdered(x -> calls[0]++);
        check(calls[0] == 0, "empty sequential stream must not invoke the action");
    }

    static void testEmptyParallelStream() {
        int[] calls = new int[1];
        IntStream.empty().parallel().forEachOrdered(x -> calls[0]++);
        check(calls[0] == 0, "empty parallel stream must not invoke the action");
    }

    static void testSequentialSum() {
        long[] sum = new long[1];
        IntStream.rangeClosed(1, 10).forEachOrdered(x -> sum[0] += x);
        check(sum[0] == 55L, "sequential forEachOrdered sum of 1..10 must be 55");
    }

    static void testParallelSumIsComplete() {
        long[] sum = new long[1];
        IntStream.rangeClosed(1, 1000).parallel().forEachOrdered(x -> sum[0] += x);
        check(sum[0] == 500500L, "parallel forEachOrdered must process all elements exactly once");
    }
}
