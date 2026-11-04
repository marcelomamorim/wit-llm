/*
 * @test
 * @summary Test for java.util.stream.ReduceOps.makeLong(long, LongBinaryOperator)
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.function.LongBinaryOperator;
import java.util.stream.StreamShape;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeLongNullOperator();
        testMakeLongReduction();
    }

    private static void testMakeLongNullOperator() {
        try {
            ReduceOps.makeLong(0L, null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testMakeLongReduction() {
        final long identity = 1L;
        LongBinaryOperator op = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left * right;
            }
        };

        ReduceOps.TerminalOp<Long, Long> reduceOp = ReduceOps.makeLong(identity, op);
        ReduceOps.AccumulatingSink<Long, Long, ?> sink = reduceOp.makeSink();

        // Begin with size 3 (arbitrary)
        sink.begin(3);

        // Accept values 2, 3, 4
        sink.accept(2L);
        sink.accept(3L);
        sink.accept(4L);

        // The expected result is 1 * 2 * 3 * 4 = 24
        Long result = sink.get();
        if (!result.equals(24L)) {
            throw new AssertionError("Reduction result expected 24 but was " + result);
        }

        // Test combine: combine with another sink with state 5
        ReduceOps.AccumulatingSink<Long, Long, ?> otherSink = reduceOp.makeSink();
        otherSink.begin(1);
        otherSink.accept(5L);

        // Combine sinks
        sink.combine(otherSink);

        // After combine, result should be 24 * 5 = 120
        Long combinedResult = sink.get();
        if (!combinedResult.equals(120L)) {
            throw new AssertionError("Combined result expected 120 but was " + combinedResult);
        }
    }
}