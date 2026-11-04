/*
 * @test
 * @summary Behavioral checks for LongPipeline.forEach through the public LongStream API (sequential and parallel paths)
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialForEachPreservesOrder();
        testParallelForEachSum();
        testEmptyStreamForEachInvokesNothing();
        testMappedStreamForEach();
        testParallelFlagReflectsForEachPath();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testSequentialForEachPreservesOrder() {
        List<Long> out = new ArrayList<>();
        LongStream.of(3L, 1L, 2L).forEach(v -> out.add(v));
        check(out.size() == 3, "expected 3 elements, got " + out.size());
        check(out.get(0) == 3L && out.get(1) == 1L && out.get(2) == 2L,
              "sequential order not preserved: " + out);
    }

    static void testParallelForEachSum() {
        AtomicLong sum = new AtomicLong();
        LongStream.rangeClosed(1, 1000).parallel().forEach(v -> sum.addAndGet(v));
        check(sum.get() == 500500L, "parallel forEach sum wrong: " + sum.get());
    }

    static void testEmptyStreamForEachInvokesNothing() {
        AtomicLong calls = new AtomicLong();
        LongStream.empty().forEach(v -> calls.incrementAndGet());
        check(calls.get() == 0L, "action invoked for empty stream: " + calls.get());

        AtomicLong parallelCalls = new AtomicLong();
        LongStream.empty().parallel().forEach(v -> parallelCalls.incrementAndGet());
        check(parallelCalls.get() == 0L, "action invoked for empty parallel stream: " + parallelCalls.get());
    }

    static void testMappedStreamForEach() {
        List<Long> out = new ArrayList<>();
        LongStream.range(0, 5).map(x -> x * 2).forEach(v -> out.add(v));
        check(out.size() == 5, "expected 5 mapped elements, got " + out.size());
        for (int i = 0; i < 5; i++) {
            check(out.get(i) == 2L * i, "mapped value wrong at " + i + ": " + out.get(i));
        }
    }

    static void testParallelFlagReflectsForEachPath() {
        check(!LongStream.of(1L).isParallel(), "sequential stream reported parallel");
        check(LongStream.of(1L).parallel().isParallel(), "parallel stream not reported parallel");
    }
}
