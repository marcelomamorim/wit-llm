/*
 * @test
 * @summary Tests for ReduceOps.makeLong method in java.util.stream
 * @run main ReduceOpsWitupTest
 */
import java.util.stream.ReduceOps;
import java.util.function.LongBinaryOperator;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeLongWithSum();
        testMakeLongWithMax();
        testMakeLongWithMin();
    }

    static void testMakeLongWithSum() {
        LongBinaryOperator sumOperator = (a, b) -> a + b;
        long identity = 0L;
        var reduceOp = ReduceOps.makeLong(identity, sumOperator);
        ReduceOps.ReduceOp.ReducingSink sink = reduceOp.makeSink();
        sink.begin(3);
        sink.accept(10L);
        sink.accept(20L);
        sink.accept(30L);
        Long result = sink.get();
        assert result.equals(60L) : "Expected 60, got " + result;
    }

    static void testMakeLongWithMax() {
        LongBinaryOperator maxOperator = (a, b) -> a > b ? a : b;
        long identity = Long.MIN_VALUE;
        var reduceOp = ReduceOps.makeLong(identity, maxOperator);
        ReduceOps.ReduceOp.ReducingSink sink = reduceOp.makeSink();
        sink.begin(4);
        sink.accept(5L);
        sink.accept(15L);
        sink.accept(10L);
        sink.accept(20L);
        Long result = sink.get();
        assert result.equals(20L) : "Expected 20, got " + result;
    }

    static void testMakeLongWithMin() {
        LongBinaryOperator minOperator = (a, b) -> a < b ? a : b;
        long identity = Long.MAX_VALUE;
        var reduceOp = ReduceOps.makeLong(identity, minOperator);
        ReduceOps.ReduceOp.ReducingSink sink = reduceOp.makeSink();
        sink.begin(3);
        sink.accept(100L);
        sink.accept(50L);
        sink.accept(75L);
        Long result = sink.get();
        assert result.equals(50L) : "Expected 50, got " + result;
    }
}
