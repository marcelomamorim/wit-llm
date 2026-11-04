/*
 * @test
 * @summary Test java.util.stream.ReduceOps.makeLong method behavior
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.stream.StreamShape;
import java.util.function.LongBinaryOperator;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeLongWithSumOperator();
        testMakeLongWithIdentityOnly();
        testMakeLongWithOperatorThatReturnsConstant();
    }

    private static void testMakeLongWithSumOperator() {
        final long identity = 0L;
        LongBinaryOperator sumOperator = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };

        ReduceOps.ReduceOp<Long, Long, ?> op = ReduceOps.makeLong(identity, sumOperator);
        if (op == null) throw new AssertionError("makeLong returned null");
        if (op.getOpShape() != StreamShape.LONG_VALUE) throw new AssertionError("StreamShape mismatch");

        ReduceOps.AccumulatingSink<Long, Long, ?> sink = op.makeSink();
        sink.begin(3);
        sink.accept(1L);
        sink.accept(2L);
        sink.accept(3L);
        Long result = sink.get();
        if (result.longValue() != 6L) throw new AssertionError("Sum operator failed: expected 6 but got " + result);

        // Test combine
        ReduceOps.AccumulatingSink<Long, Long, ?> otherSink = op.makeSink();
        otherSink.begin(2);
        otherSink.accept(4L);
        otherSink.accept(5L);

        sink.combine(otherSink);
        Long combinedResult = sink.get();
        if (combinedResult.longValue() != 15L) throw new AssertionError("Combine failed: expected 15 but got " + combinedResult);
    }

    private static void testMakeLongWithIdentityOnly() {
        final long identity = 42L;
        LongBinaryOperator identityOperator = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left; // ignores right
            }
        };

        ReduceOps.ReduceOp<Long, Long, ?> op = ReduceOps.makeLong(identity, identityOperator);
        ReduceOps.AccumulatingSink<Long, Long, ?> sink = op.makeSink();
        sink.begin(0);
        Long result = sink.get();
        if (result.longValue() != identity) throw new AssertionError("Identity only operator failed: expected " + identity + " but got " + result);
    }

    private static void testMakeLongWithOperatorThatReturnsConstant() {
        final long identity = 1L;
        LongBinaryOperator constantOperator = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return 7L; // always returns 7
            }
        };

        ReduceOps.ReduceOp<Long, Long, ?> op = ReduceOps.makeLong(identity, constantOperator);
        ReduceOps.AccumulatingSink<Long, Long, ?> sink = op.makeSink();
        sink.begin(2);
        sink.accept(10L);
        sink.accept(20L);
        Long result = sink.get();
        if (result.longValue() != 7L) throw new AssertionError("Constant operator failed: expected 7 but got " + result);
    }
}
