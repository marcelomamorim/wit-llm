/*
 * @test
 * @summary Verifies the unbounded IntStream returned by ThreadLocalRandom.ints()
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.PrimitiveIterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        IntStream s = tlr.ints();
        check(s != null, "ints() returned null");
        check(!s.isParallel(), "ints() stream must be sequential by default");

        check(tlr.ints().limit(0).count() == 0L, "limit(0) must yield no elements");
        check(tlr.ints().limit(1000).count() == 1000L, "limit(1000) must yield 1000 elements");
        check(tlr.ints().parallel().limit(500).count() == 500L, "parallel limit(500) must yield 500 elements");

        PrimitiveIterator.OfInt it = tlr.ints().iterator();
        for (int i = 0; i < 10; i++) {
            check(it.hasNext(), "unbounded ints() iterator must have next");
            it.nextInt();
        }
        check(it.hasNext(), "unbounded ints() iterator must remain non-empty");
    }
}
