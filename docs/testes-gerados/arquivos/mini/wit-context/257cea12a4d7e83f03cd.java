/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(IntConsumer) for correct behavior and NPE on null
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        final int[] data = {1, 2, 3, 4, 5};
        final int[] collected = new int[data.length];
        final int[] index = {0};

        Spliterators.IntArraySpliterator spliterator = new Spliterators.IntArraySpliterator(data, 0, data.length, 0);

        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                collected[index[0]++] = value;
            }
        });

        if (index[0] != data.length) {
            throw new AssertionError("forEachRemaining did not consume all elements");
        }

        for (int i = 0; i < data.length; i++) {
            if (collected[i] != data[i]) {
                throw new AssertionError("forEachRemaining consumed incorrect element at index " + i);
            }
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        final int[] data = {1, 2, 3};
        Spliterators.IntArraySpliterator spliterator = new Spliterators.IntArraySpliterator(data, 0, data.length, 0);

        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
