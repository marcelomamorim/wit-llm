/*
 * @test
 * @summary Test DoublePipeline.accept(double) method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntConsumer;
import java.util.stream.DoublePipeline;
import java.util.stream.AbstractPipeline;
import java.util.Spliterator;
import java.util.function.Supplier;

public class DoublePipelineWitupTest {

    static class TestDoublePipeline extends DoublePipeline<Double> {
        IntConsumer downstream;
        DoubleUnaryOperator mapper;

        TestDoublePipeline(IntConsumer downstream, DoubleUnaryOperator mapper) {
            super(new Supplier<Spliterator<Double>>() {
                public Spliterator<Double> get() {
                    return null;
                }
            }, 0, false);
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void accept(double t) {
            downstream.accept(mapper.applyAsInt(t));
        }
    }

    public static void main(String[] args) {
        final int[] result = new int[1];

        // Mapper: multiply input double by 2 and cast to int
        DoubleUnaryOperator mapper = new DoubleUnaryOperator() {
            public double applyAsDouble(double operand) {
                return operand * 2;
            }

            public int applyAsInt(double operand) {
                return (int) (operand * 2);
            }
        };

        // Downstream consumer: store accepted int value
        IntConsumer downstream = new IntConsumer() {
            public void accept(int value) {
                result[0] = value;
            }
        };

        TestDoublePipeline pipeline = new TestDoublePipeline(downstream, mapper);

        // Test with positive double
        pipeline.accept(3.5);
        if (result[0] != 7) {
            throw new AssertionError("Expected 7 but got " + result[0]);
        }

        // Test with zero
        pipeline.accept(0.0);
        if (result[0] != 0) {
            throw new AssertionError("Expected 0 but got " + result[0]);
        }

        // Test with negative double
        pipeline.accept(-2.25);
        if (result[0] != -4) {
            throw new AssertionError("Expected -4 but got " + result[0]);
        }
    }
}
