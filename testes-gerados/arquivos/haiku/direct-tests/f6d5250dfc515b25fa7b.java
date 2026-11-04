/*
 * @test
 * @summary Checks LongStream.reduce(identity, operator), which delegates to ReduceOps.makeLong, through its public API
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.LongStream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        check(LongStream.empty().reduce(42L, (a, b) -> a + b) == 42L, "empty stream must return identity");
        check(LongStream.of(1L, 2L, 3L, 4L).reduce(0L, (a, b) -> a + b) == 10L, "sum reduction failed");
        check(LongStream.of(1L, 2L, 3L, 4L).reduce(1L, (a, b) -> a * b) == 24L, "product reduction failed");
        check(LongStream.of(3L, 9L, 2L).reduce(Long.MIN_VALUE, (a, b) -> Math.max(a, b)) == 9L, "max reduction failed");
        check(LongStream.of(5L).reduce(7L, (a, b) -> a + b) == 12L, "single element reduction failed");
        check(LongStream.of(10L, 3L, 2L).reduce(0L, (a, b) -> a - b) == -15L, "sequential order not preserved");

        boolean npe = false;
        try {
            LongStream.of(1L).reduce(0L, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null operator must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
