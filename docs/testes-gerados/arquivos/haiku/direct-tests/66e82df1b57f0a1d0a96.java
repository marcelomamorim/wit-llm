/*
 * @test
 * @summary Verifies that IntStream.forEach, which dispatches to IntPipeline.forEach, visits every element
 * @run main IntPipelineWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testSequentialOrder();
        testEmptyStream();
        testSumRange();
        testParallelSum();
    }

    static void testSequentialOrder() {
        List<Integer> seen = new ArrayList<>();
        IntStream.of(3, 1, 2).forEach(i -> seen.add(i));
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(1);
        expected.add(2);
        if (!seen.equals(expected)) {
            throw new RuntimeException("Expected " + expected + " but got " + seen);
        }
    }

    static void testEmptyStream() {
        AtomicInteger count = new AtomicInteger();
        IntStream.empty().forEach(i -> count.incrementAndGet());
        if (count.get() != 0) {
            throw new RuntimeException("Expected 0 invocations but got " + count.get());
        }
    }

    static void testSumRange() {
        AtomicInteger sum = new AtomicInteger();
        IntStream.range(0, 100).forEach(i -> sum.addAndGet(i));
        if (sum.get() != 4950) {
            throw new RuntimeException("Expected 4950 but got " + sum.get());
        }
    }

    static void testParallelSum() {
        AtomicInteger sum = new AtomicInteger();
        IntStream.range(0, 1000).parallel().forEach(i -> sum.addAndGet(i));
        if (sum.get() != 499500) {
            throw new RuntimeException("Expected 499500 but got " + sum.get());
        }
    }
}
