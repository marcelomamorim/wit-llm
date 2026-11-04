/*
 * @test
 * @summary Verifies LongStream.generate spliterator tryAdvance delegates to the supplier and rejects null actions
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testAdvancesOneElementPerCall();
        testNullActionThrowsAndDoesNotConsume();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAdvancesOneElementPerCall() {
        long[] counter = {0L};
        Spliterator.OfLong sp = LongStream.generate(() -> counter[0]++).spliterator();
        long[] seen = new long[3];
        int[] idx = {0};
        LongConsumer consumer = v -> seen[idx[0]++] = v;
        for (int i = 0; i < 3; i++) {
            check(sp.tryAdvance(consumer), "tryAdvance must return true on each call");
        }
        check(counter[0] == 3L, "supplier must be invoked once per advance, was " + counter[0]);
        check(seen[0] == 0L && seen[1] == 1L && seen[2] == 2L,
              "consumer must receive supplier values in order");
    }

    static void testNullActionThrowsAndDoesNotConsume() {
        long[] counter = {0L};
        Spliterator.OfLong sp = LongStream.generate(() -> {
            counter[0]++;
            return 1L;
        }).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
        check(counter[0] == 0L, "supplier must not be invoked when action is null");
    }
}
