/*
 * @test
 * @summary Behavioral tests for Stream.flatMap (ReferencePipeline.flatMap) via public API
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            Stream.of(1, 2).flatMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "flatMap(null) must throw NullPointerException");
    }

    static void testConcatenatesResults() {
        List<Integer> r = Stream.of(1, 2, 3)
                .flatMap(i -> Stream.of(i, i * 10))
                .collect(Collectors.toList());
        check(r.equals(Arrays.asList(1, 10, 2, 20, 3, 30)),
              "unexpected result: " + r);
    }

    static void testNullResultIsSkipped() {
        List<Integer> r = Stream.of(1, 2, 3)
                .flatMap(i -> i == 2 ? null : Stream.of(i))
                .collect(Collectors.toList());
        check(r.equals(Arrays.asList(1, 3)), "null result not skipped: " + r);
    }

    static void testEmptyInnerStreams() {
        long count = Stream.of(1, 2, 3)
                .flatMap(i -> Stream.<Integer>empty())
                .count();
        check(count == 0L, "expected 0 elements, got " + count);
    }

    static void testShortCircuitWithLimit() {
        List<Integer> r = Stream.iterate(1, i -> i + 1)
                .flatMap(i -> Stream.of(i, -i))
                .limit(3)
                .collect(Collectors.toList());
        check(r.equals(Arrays.asList(1, -1, 2)),
              "unexpected short-circuit result: " + r);
    }

    static void testInnerStreamIsClosed() {
        boolean[] closed = {false};
        Stream<Integer> inner = Stream.of(1, 2).onClose(() -> closed[0] = true);
        List<Integer> r = Stream.of(0)
                .flatMap(x -> inner)
                .collect(Collectors.toList());
        check(r.equals(Arrays.asList(1, 2)), "unexpected result: " + r);
        check(closed[0], "inner stream returned by mapper was not closed");
    }

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testConcatenatesResults();
        testNullResultIsSkipped();
        testEmptyInnerStreams();
        testShortCircuitWithLimit();
        testInnerStreamIsClosed();
    }
}
