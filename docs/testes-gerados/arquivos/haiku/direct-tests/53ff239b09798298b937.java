/*
 * @test
 * @summary Verifies IntPipeline.forEach through the public IntStream API, for sequential and parallel pipelines
 * @run main IntPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialForEachPreservesOrder();
        testSequentialForEachAfterMap();
        testSequentialForEachAfterFilter();
        testSequentialForEachOnEmptyStreamDoesNotInvoke();
        testParallelForEachVisitsAllElements();
        testParallelForEachOnEmptyStreamDoesNotInvoke();
    }

    static void testSequentialForEachPreservesOrder() {
        List<Integer> out = new ArrayList<>();
        IntStream.of(3, 1, 2).forEach(v -> out.add(v));
        check(out.size() == 3, "expected 3 elements, got " + out.size());
        check(out.get(0) == 3 && out.get(1) == 1 && out.get(2) == 2,
              "unexpected order: " + out);
    }

    static void testSequentialForEachAfterMap() {
        List<Integer> out = new ArrayList<>();
        IntStream.of(1, 2, 3).map(x -> x * 10).forEach(v -> out.add(v));
        check(out.equals(List.of(10, 20, 30)) || out.toString().equals("[10, 20, 30]"),
              "unexpected mapped values: " + out);
    }

    static void testSequentialForEachAfterFilter() {
        List<Integer> out = new ArrayList<>();
        IntStream.range(0, 10).filter(x -> x % 2 == 0).forEach(v -> out.add(v));
        check(out.toString().equals("[0, 2, 4, 6, 8]"), "unexpected filtered values: " + out);
    }

    static void testSequentialForEachOnEmptyStreamDoesNotInvoke() {
        AtomicInteger calls = new AtomicInteger();
        IntStream.empty().forEach(v -> calls.incrementAndGet());
        check(calls.get() == 0, "action invoked " + calls.get() + " times on empty stream");
    }

    static void testParallelForEachVisitsAllElements() {
        AtomicLong sum = new AtomicLong();
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, 1000).parallel().forEach(v -> {
            sum.addAndGet(v);
            count.incrementAndGet();
        });
        check(count.get() == 1000, "expected 1000 invocations, got " + count.get());
        check(sum.get() == 499500L, "unexpected sum: " + sum.get());
    }

    static void testParallelForEachOnEmptyStreamDoesNotInvoke() {
        AtomicInteger calls = new AtomicInteger();
        IntStream.empty().parallel().forEach(v -> calls.incrementAndGet());
        check(calls.get() == 0, "action invoked " + calls.get() + " times on empty parallel stream");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
