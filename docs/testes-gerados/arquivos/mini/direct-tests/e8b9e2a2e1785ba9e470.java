/*
 * @test
 * @summary Test DoublePipeline.filter method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamShape;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to test filter
    static class TestDoublePipeline extends DoublePipeline<Double> {

        TestDoublePipeline(Spliterator<Double> source) {
            super(source, StreamOpFlag.NOT_SIZED, false);
        }

        TestDoublePipeline(AbstractPipeline<?, Double, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEach(DoubleConsumer action) {
            Objects.requireNonNull(action);
            Spliterator<Double> spliterator = sourceSpliterator();
            spliterator.forEachRemaining(d -> action.accept(d));
        }

        private Spliterator<Double> sourceSpliterator() {
            return Spliterators.spliterator(new double[] {1.0, 2.0, 3.0, 4.0, 5.0}, 0);
        }
    }

    public static void main(String[] args) {
        // Create a source DoublePipeline with known elements
        TestDoublePipeline source = new TestDoublePipeline(
            Spliterators.spliterator(new Double[] {1.0, 2.0, 3.0, 4.0, 5.0}, 0));

        // Test 1: filter with predicate that accepts all elements
        DoubleStream allPass = source.filter(new DoublePredicate() {
            public boolean test(double value) {
                return true;
            }
        });
        double sumAll = allPass.sum();
        if (sumAll != 15.0) {
            throw new AssertionError("filter with always-true predicate failed: expected sum 15.0 but got " + sumAll);
        }

        // Test 2: filter with predicate that accepts no elements
        DoubleStream nonePass = source.filter(new DoublePredicate() {
            public boolean test(double value) {
                return false;
            }
        });
        double sumNone = nonePass.sum();
        if (sumNone != 0.0) {
            throw new AssertionError("filter with always-false predicate failed: expected sum 0.0 but got " + sumNone);
        }

        // Test 3: filter with predicate that accepts even numbers only
        DoubleStream evenPass = source.filter(new DoublePredicate() {
            public boolean test(double value) {
                return ((int) value) % 2 == 0;
            }
        });
        double sumEven = evenPass.sum();
        if (sumEven != 6.0) { // 2 + 4 = 6
            throw new AssertionError("filter with even predicate failed: expected sum 6.0 but got " + sumEven);
        }

        // Test 4: filter with null predicate should throw NullPointerException
        try {
            source.filter(null);
            throw new AssertionError("filter with null predicate did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
