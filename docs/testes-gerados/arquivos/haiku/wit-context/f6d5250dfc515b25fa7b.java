/*
 * @test
 * @summary Public LongStream.reduce path that reaches ReduceOps.makeLong: identity, empty stream, parallel combine and null operator NPE
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.LongStream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        check(LongStream.of(1L, 2L, 3L).reduce(0L, Long::sum) == 6L, "sum of 1,2,3 with identity 0");
        check(LongStream.empty().reduce(42L, Long::sum) == 42L, "empty stream must return identity");
        check(LongStream.of(5L, -3L, 9L).reduce(Long.MIN_VALUE, Math::max) == 9L, "max reduction");
        check(LongStream.rangeClosed(1L, 100L).parallel().reduce(0L, Long::sum) == 5050L, "parallel sum must combine correctly");
        check(LongStream.rangeClosed(1L, 1000L).parallel().reduce(Long.MIN_VALUE, Math::max) == 1000L, "parallel max must combine correctly");

        boolean npe = false;
        try {
            LongStream.of(1L, 2L).reduce(0L, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null operator must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
