/*
 * @test
 * @summary Test tryAdvance method of Stream class
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Consumer;

public class StreamWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNonNullConsumer();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithNonNullConsumer() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        final int[] count = {0};
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                count[0]++;
                // do nothing else
            }
        };
        boolean result = stream.tryAdvance(consumer);
        assert result : "tryAdvance should return true";
        assert count[0] == 1 : "Consumer should be accepted exactly once";
    }

    static void testTryAdvanceWithNullConsumer() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            stream.tryAdvance(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
