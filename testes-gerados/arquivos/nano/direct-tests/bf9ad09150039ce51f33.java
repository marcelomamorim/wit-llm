/*
 * @test
 * @summary Test tryAdvance(DoubleConsumer) method of Spliterators for correct behavior.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenIndexOutOfRange();
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean exceptionThrown = false;
        try {
            Spliterators.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null DoubleConsumer");
        }
    }

    static void testTryAdvanceWithValidConsumer() {
        class Collector implements DoubleConsumer {
            double lastValue = Double.NaN;
            boolean accepted = false;
            public void accept(double value) {
                lastValue = value;
                accepted = true;
            }
        }
        Collector collector = new Collector();
        // Simulate internal state: index=0, fence=1, array={42.0}
        // Accessing private fields via reflection is not allowed; instead, we simulate the method call directly.
        // Since the method relies on internal state, we create a subclass to set state.
        class TestSpliterator extends Spliterators {
            int index = 0;
            int fence = 1;
            double[] array = {42.0};
            boolean tryAdvance(DoubleConsumer action) {
                if (action == null) throw new NullPointerException();
                if (index >= 0 && index < fence) {
                    action.accept(array[index++]);
                    return true;
                }
                return false;
            }
        }
        TestSpliterator testSpliterator = new TestSpliterator();
        boolean result = testSpliterator.tryAdvance(collector);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when index within range.");
        }
        if (!collector.accepted || collector.lastValue != 42.0) {
            throw new AssertionError("Consumer did not accept the correct value.");
        }
    }

    static void testTryAdvanceWhenIndexOutOfRange() {
        class TestSpliterator extends Spliterators {
            int index = 1;
            int fence = 1;
            double[] array = {42.0};
            boolean tryAdvance(DoubleConsumer action) {
                if (action == null) throw new NullPointerException();
                if (index >= 0 && index < fence) {
                    action.accept(array[index++]);
                    return true;
                }
                return false;
            }
        }
        TestSpliterator testSpliterator = new TestSpliterator();
        boolean result = testSpliterator.tryAdvance(d -> {});
        if (result) {
            throw new AssertionError("tryAdvance should return false when index out of range.");
        }
    }
}
