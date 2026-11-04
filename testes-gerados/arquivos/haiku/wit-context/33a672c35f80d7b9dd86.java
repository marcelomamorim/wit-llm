/*
 * @test
 * @summary Behavioral checks for ThreadLocalRandom.ints() returning an unbounded, sequential IntStream
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        IntStream s = ThreadLocalRandom.current().ints();
        check(s != null, "ints() returned null");
        check(!s.isParallel(), "ints() stream must be sequential");

        long count = ThreadLocalRandom.current().ints().limit(1000).count();
        check(count == 1000L, "expected 1000 elements but got " + count);

        long zero = ThreadLocalRandom.current().ints().limit(0).count();
        check(zero == 0L, "expected 0 elements but got " + zero);

        int[] values = ThreadLocalRandom.current().ints().limit(256).toArray();
        check(values.length == 256, "expected array length 256 but got " + values.length);

        IntStream a = ThreadLocalRandom.current().ints();
        IntStream b = ThreadLocalRandom.current().ints();
        check(a != b, "each call to ints() should return a new stream");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
