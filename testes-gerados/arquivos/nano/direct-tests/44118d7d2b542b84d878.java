/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(DoubleConsumer)
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithNull();
        testForEachRemainingWithValidConsumer();
    }

    static void testForEachRemainingWithNull() {
        try {
            Spliterators.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }

    static void testForEachRemainingWithValidConsumer() {
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer consumer = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                sb.append(value).append(",");
            }
        };
        // Create a dummy iterator with forEachRemaining method
        java.util.Iterator<Double> iterator = new java.util.Iterator<Double>() {
            private final Double[] values = {1.1, 2.2, 3.3};
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < values.length;
            }
            @Override
            public Double next() {
                return values[index++];
            }
        };
        // Wrap iterator to call forEachRemaining
        java.util.Spliterator<Double> spliterator = java.util.Spliterators.spliteratorUnknownSize(iterator, 0);
        // Use forEachRemaining
        spliterator.forEachRemaining(consumer);
        String result = sb.toString();
        if (!result.equals("1.1,2.2,3.3,")) {
            throw new AssertionError("Unexpected output: " + result);
        }
    }
}
