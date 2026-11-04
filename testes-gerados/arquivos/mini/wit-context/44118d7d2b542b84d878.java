/*
 * @test
 * @summary Test java.util.Spliterators.DoubleIteratorSpliterator.forEachRemaining(DoubleConsumer) for NPE on null argument and normal operation
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        Spliterators.DoubleIteratorSpliterator spliterator = new Spliterators.DoubleIteratorSpliterator(new double[] {1.0, 2.0}, 0, 2, 0);
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer action = new DoubleConsumer() {
            public void accept(double value) {
                sb.append((int)value).append(",");
            }
        };
        double[] array = {10.0, 20.0, 30.0};
        Spliterators.DoubleIteratorSpliterator spliterator = new Spliterators.DoubleIteratorSpliterator(array, 0, array.length, 0);
        spliterator.forEachRemaining(action);
        String result = sb.toString();
        if (!result.equals("10,20,30,")) {
            throw new RuntimeException("Unexpected forEachRemaining output: " + result);
        }
    }
}
