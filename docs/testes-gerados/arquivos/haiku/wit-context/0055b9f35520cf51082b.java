/*
 * @test
 * @summary Witup-generated tests for LongPipeline.forEach observable behavior
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialForEachVisitsAllElementsInOrder();
        testParallelForEachVisitsAllElements();
        testReusedStreamThrowsIllegalStateException();
        testNullActionThrowsNullPointerException();
    }

    static void testSequentialForEachVisitsAllElementsInOrder() {
        List<Long> seen = new ArrayList<>();
        LongStream.of(3L, 1L, 2L).forEach(seen::add);
        check(seen.size() == 3, "expected 3 elements but got " + seen.size());
        check(seen.get(0) == 3L && seen.get(1) == 1L && seen.get(2) == 2L,
              "unexpected order: " + seen);
    }

    static void testParallelForEachVisitsAllElements() {
        AtomicLong sum = new AtomicLong();
        AtomicLong count = new AtomicLong();
        LongStream.rangeClosed(1L, 1000L).parallel().forEach(x -> {
            sum.addAndGet(x);
            count.incrementAndGet();
        });
        check(count.get() == 1000L, "expected 1000 visits but got " + count.get());
        check(sum.get() == 500500L, "expected sum 500500 but got " + sum.get());
    }

    static void testReusedStreamThrowsIllegalStateException() {
        LongStream s = LongStream.of(1L, 2L);
        s.forEach(x -> { });
        try {
            s.forEach(x -> { });
            fail("expected IllegalStateException on reuse");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testNullActionThrowsNullPointerException() {
        try {
            LongStream.of(1L).forEach(null);
            fail("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
