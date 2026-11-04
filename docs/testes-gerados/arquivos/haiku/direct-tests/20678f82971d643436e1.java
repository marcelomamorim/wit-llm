/*
 * @test
 * @summary Tests for Stream.concat: element order, null arguments, parallel flag and close handlers
 * @run main StreamWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOrderAndCount() {
        List<String> got = Stream.concat(Stream.of("a", "b"), Stream.of("c"))
                .collect(Collectors.toList());
        check(got.equals(Arrays.asList("a", "b", "c")), "unexpected order: " + got);

        long count = Stream.concat(Stream.of(1, 2), Stream.of(3)).count();
        check(count == 3L, "unexpected count: " + count);
    }

    static void testNullFirstArgument() {
        boolean thrown = false;
        try {
            Stream.concat(null, Stream.of("x"));
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null first argument");
    }

    static void testNullSecondArgument() {
        boolean thrown = false;
        try {
            Stream.concat(Stream.of("x"), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null second argument");
    }

    static void testParallelFlag() {
        Stream<Integer> par = Stream.concat(Stream.of(1).parallel(), Stream.of(2));
        check(par.isParallel(), "expected parallel result when first argument is parallel");

        Stream<Integer> seq = Stream.concat(Stream.of(1), Stream.of(2));
        check(!seq.isParallel(), "expected sequential result when both arguments are sequential");
    }

    static void testCloseHandlersInvoked() {
        AtomicInteger closes = new AtomicInteger();
        Stream<String> a = Stream.of("a").onClose(() -> { closes.incrementAndGet(); });
        Stream<String> b = Stream.of("b").onClose(() -> { closes.incrementAndGet(); });
        Stream<String> r = Stream.concat(a, b);
        check(closes.get() == 0, "handlers should not run before close");
        r.close();
        check(closes.get() == 2, "expected both close handlers to run, got " + closes.get());
    }

    public static void main(String[] args) {
        testOrderAndCount();
        testNullFirstArgument();
        testNullSecondArgument();
        testParallelFlag();
        testCloseHandlersInvoked();
    }
}
