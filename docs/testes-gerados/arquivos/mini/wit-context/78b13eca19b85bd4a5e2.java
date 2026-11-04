/*
 * @test
 * @summary Test Streams.RangeIntSpliterator.forEachRemaining with null and valid IntConsumer
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.function.IntConsumer;
import java.util.Objects;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        Streams.RangeIntSpliterator ris = new Streams.RangeIntSpliterator(0, 1, true);
        try {
            ris.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        final StringBuilder sb = new StringBuilder();
        Streams.RangeIntSpliterator ris = new Streams.RangeIntSpliterator(0, 3, true);
        ris.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        // The closed range from 0 to 3 includes 0,1,2 and last element 3
        if (!"0,1,2,3,".equals(result)) {
            throw new Error("Unexpected forEachRemaining output: " + result);
        }
    }
}
