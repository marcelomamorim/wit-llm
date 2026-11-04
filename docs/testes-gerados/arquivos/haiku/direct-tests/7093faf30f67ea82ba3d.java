/*
 * @test
 * @summary Basic observable behavior of ThreadLocalRandom.longs()
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        LongStream stream = tlr.longs();
        check(stream != null, "longs() must return a non-null stream");

        long count = ThreadLocalRandom.current().longs().limit(100).count();
        check(count == 100L, "expected 100 elements but got " + count);

        long zero = ThreadLocalRandom.current().longs().limit(0).count();
        check(zero == 0L, "expected 0 elements but got " + zero);

        long[] values = ThreadLocalRandom.current().longs().limit(50).toArray();
        check(values.length == 50, "toArray length expected 50 but got " + values.length);

        boolean allSame = true;
        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[0]) {
                allSame = false;
                break;
            }
        }
        check(!allSame, "generated longs should not all be equal");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
