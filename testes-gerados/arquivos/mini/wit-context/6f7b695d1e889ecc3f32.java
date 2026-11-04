/*
 * @test
 * @summary Test IntPipeline.accept(int) method for correct delegation and exception propagation
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamOpFlag;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing
    static class TestIntPipeline extends IntPipeline<Object> {

        IntConsumer downstream;

        TestIntPipeline(IntConsumer downstream) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return null;
                }
            }, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, false);
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            // Delegate to downstream IntConsumer
            downstream.accept(t);
        }

        // Other abstract methods from IntStream can be left unimplemented for this test
    }

    // IntConsumer that records the last accepted value
    static class RecordingIntConsumer implements IntConsumer {
        int lastValue = Integer.MIN_VALUE;
        boolean accepted = false;

        public void accept(int value) {
            lastValue = value;
            accepted = true;
        }
    }

    // IntConsumer that throws IllegalStateException on accept
    static class ThrowingIntConsumer implements IntConsumer {
        public void accept(int value) {
            throw new IllegalStateException("Forced exception");
        }
    }

    public static void main(String[] args) {
        testAcceptDelegatesValue();
        testAcceptPropagatesException();
        System.out.println("PASS");
    }

    private static void testAcceptDelegatesValue() {
        RecordingIntConsumer ric = new RecordingIntConsumer();
        TestIntPipeline pipeline = new TestIntPipeline(ric);
        int testValue = 12345;
        pipeline.accept(testValue);
        if (!ric.accepted) {
            throw new AssertionError("Downstream consumer was not called");
        }
        if (ric.lastValue != testValue) {
            throw new AssertionError("Downstream consumer received wrong value: expected " + testValue + " but got " + ric.lastValue);
        }
    }

    private static void testAcceptPropagatesException() {
        ThrowingIntConsumer throwingConsumer = new ThrowingIntConsumer();
        TestIntPipeline pipeline = new TestIntPipeline(throwingConsumer);
        try {
            pipeline.accept(0);
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
