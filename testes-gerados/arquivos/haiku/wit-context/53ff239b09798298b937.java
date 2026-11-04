/*
 * @test
 * @summary Witup-derived checks for IntStream.forEach ordering, parallel summation and reuse of consumed streams
 * @run main IntPipelineWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialForEachPreservesOrder();
        testParallelForEachVisitsAllElements();
        testSequentialReuseThrowsIllegalState();
        testParallelReuseThrowsIllegalState();
    }

    static void testSequentialForEachPreservesOrder() {
        List<Integer> seen = new ArrayList<>();
        IntStream.of(3, 1, 2).forEach(v -> seen.add(v));
        check(seen.equals(Arrays.asList(3, 1, 2)),
              "sequential forEach order: " + seen);
    }

    static void testParallelForEachVisitsAllElements() {
        AtomicLong sum = new AtomicLong();
        IntStream.range(0, 1000).parallel().forEach(v -> sum.addAndGet(v));
        check(sum.get() == 499500L,
              "parallel forEach sum: " + sum.get());
    }

    static void testSequentialReuseThrowsIllegalState() {
        IntStream s = IntStream.range(0, 10);
        s.forEach(v -> { });
        boolean thrown = false;
        try {
            s.forEach(v -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "sequential reuse should throw IllegalStateException");
    }

    static void testParallelReuseThrowsIllegalState() {
        IntStream s = IntStream.range(0, 10).parallel();
        s.forEach(v -> { });
        boolean thrown = false;
        try {
            s.forEach(v -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "parallel reuse should throw IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
