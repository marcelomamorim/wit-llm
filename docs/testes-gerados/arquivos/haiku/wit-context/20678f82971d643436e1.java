/*
 * @test
 * @summary Witup-generated checks for Stream.concat null handling and basic observable behavior
 * @run main StreamWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testNullFirstArgumentThrowsNPE();
        testNullSecondArgumentThrowsNPE();
        testConcatPreservesOrder();
        testParallelFlagPropagates();
        testSequentialFlagIsFalseWhenBothSequential();
        testCloseHandlersRunOnClose();
    }

    static void testNullFirstArgumentThrowsNPE() {
        try {
            Stream.<Integer>concat(null, Stream.of(1));
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when a is null");
    }

    static void testNullSecondArgumentThrowsNPE() {
        try {
            Stream.<Integer>concat(Stream.of(1), null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when b is null");
    }

    static void testConcatPreservesOrder() {
        List<Integer> result = Stream.concat(Stream.of(1, 2), Stream.of(3))
                .collect(Collectors.toList());
        check(Arrays.asList(1, 2, 3).equals(result), "unexpected concat order: " + result);
    }

    static void testParallelFlagPropagates() {
        Stream<Integer> p = Stream.of(1).parallel();
        Stream<Integer> c = Stream.concat(p, Stream.of(2));
        check(c.isParallel(), "concat should be parallel when a is parallel");

        Stream<Integer> c2 = Stream.concat(Stream.of(1), Stream.of(2).parallel());
        check(c2.isParallel(), "concat should be parallel when b is parallel");
    }

    static void testSequentialFlagIsFalseWhenBothSequential() {
        Stream<Integer> c = Stream.concat(Stream.of(1), Stream.of(2));
        check(!c.isParallel(), "concat of sequential streams should be sequential");
    }

    static void testCloseHandlersRunOnClose() {
        AtomicInteger counter = new AtomicInteger();
        Stream<Integer> a = Stream.of(1).onClose(() -> counter.incrementAndGet());
        Stream<Integer> b = Stream.of(2).onClose(() -> counter.incrementAndGet());
        Stream<Integer> c = Stream.concat(a, b);
        c.close();
        check(counter.get() == 2, "expected both close handlers to run, got " + counter.get());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
