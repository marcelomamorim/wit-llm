/*
 * @test
 * @summary Tests for ReduceOps.makeInt method to ensure correct reduction behavior.
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.function.IntBinaryOperator;
import java.util.OptionalInt;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeIntWithSumOperator();
        testMakeIntWithMaxOperator();
        testMakeIntWithEmptyInput();
    }

    static void testMakeIntWithSumOperator() {
        IntBinaryOperator sumOperator = (a, b) -> a + b;
        var reduceOp = ReduceOps.makeInt(sumOperator);
        int[] input = {1, 2, 3, 4};
        int result = reduceStream(input, reduceOp);
        assert result == 10 : "Expected sum 10, but got " + result;
    }

    static void testMakeIntWithMaxOperator() {
        IntBinaryOperator maxOperator = (a, b) -> a > b ? a : b;
        var reduceOp = ReduceOps.makeInt(maxOperator);
        int[] input = {1, 5, 3, 4};
        int result = reduceStream(input, reduceOp);
        assert result == 5 : "Expected max 5, but got " + result;
    }

    static void testMakeIntWithEmptyInput() {
        IntBinaryOperator sumOperator = (a, b) -> a + b;
        var reduceOp = ReduceOps.makeInt(sumOperator);
        int[] input = {};
        int result = reduceStream(input, reduceOp);
        assert result == 0 : "Expected default 0 for empty input, but got " + result;
    }

    static int reduceStream(int[] array, ReduceOps.TerminalOp<Integer, OptionalInt> op) {
        OptionalInt resultOpt = java.util.Arrays.stream(array).collect(
            new java.util.stream.Collector<Integer, ?, OptionalInt>() {
                private ReduceOps.TerminalOp<Integer, OptionalInt> terminalOp = op;
                private Object sink;

                @Override
                public java.util.stream.Collector.Characteristics[] characteristics() {
                    return new java.util.stream.Collector.Characteristics[0];
                }

                @Override
                public java.util.function.Supplier<Object> supplier() {
                    return () -> {
                        class Sink implements java.util.stream.Sink.OfInt {
                            boolean empty = true;
                            int state = 0;
                            @Override public void accept(int t) {
                                if (empty) {
                                    empty = false;
                                    state = t;
                                } else {
                                    state = ((IntBinaryOperator) ((java.util.stream.ReduceOps.TerminalOp) terminalOp).reducer).applyAsInt(state, t);
                                }
                            }
                            @Override public void begin(long size) {}
                            @Override public void end() {}
                            public OptionalInt get() {
                                return empty ? OptionalInt.empty() : OptionalInt.of(state);
                            }
                        }
                        return new Sink();
                    };
                }

                @Override
                public java.util.function.BiConsumer<Object, Integer> accumulator() {
                    return (sink, t) -> {
                        ((java.util.stream.Sink.OfInt) sink).accept(t);
                    };
                }

                @Override
                public java.util.function.BinaryOperator<Object> combiner() {
                    return (a, b) -> {
                        // Not used in sequential stream
                        return a;
                    };
                }

                @Override
                public java.util.stream.Collector.Characteristics[] characteristics() {
                    return new java.util.stream.Collector.Characteristics[0];
                }

                @Override
                public java.util.stream.Collector<Integer, ?, OptionalInt> build() {
                    return this;
                }
            }
        ).get();
        return resultOpt.orElse(0);
    }
}
