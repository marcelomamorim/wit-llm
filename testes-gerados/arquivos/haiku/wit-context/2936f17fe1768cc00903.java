/*
 * @test
 * @summary Witup-generated checks for DoublePipeline.noneMatch via the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        DoublePredicate isNegative = d -> d < 0;

        check(DoubleStream.of(1.0, 2.0, 3.0).noneMatch(isNegative), "noneMatch should be true when no element matches");
        check(!DoubleStream.of(1.0, -2.0, 3.0).noneMatch(isNegative), "noneMatch should be false when an element matches");
        check(DoubleStream.empty().noneMatch(isNegative), "noneMatch on empty stream should be true");

        DoubleStream consumed = DoubleStream.of(1.0);
        consumed.noneMatch(isNegative);
        try {
            consumed.noneMatch(isNegative);
            throw new RuntimeException("reused stream should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected: stream already operated upon or closed
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
