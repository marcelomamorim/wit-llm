/*
 * @test
 * @summary Verifies that IntPipeline.accept widens each int to long through the public asLongStream path
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class IntPipelineWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        long[] expected = {1L, -2L, 2147483647L, -2147483648L};
        long[] actual = IntStream.of(1, -2, Integer.MAX_VALUE, Integer.MIN_VALUE)
                .asLongStream().toArray();
        check(Arrays.equals(expected, actual),
              "sign-extended widening mismatch: " + Arrays.toString(actual));

        long sum = IntStream.of(Integer.MAX_VALUE, Integer.MAX_VALUE)
                .asLongStream().sum();
        check(sum == 4294967294L, "sum must not overflow int: " + sum);

        long[] empty = IntStream.empty().asLongStream().toArray();
        check(empty.length == 0, "empty stream must stay empty");

        long parallelSum = IntStream.range(0, 1000).parallel()
                .asLongStream().sum();
        check(parallelSum == 499500L, "parallel widening sum mismatch: " + parallelSum);

        long[] mapped = IntStream.of(3, 4)
                .asLongStream().map(x -> x * 1000000000L).toArray();
        check(Arrays.equals(new long[]{3000000000L, 4000000000L}, mapped),
              "long values must be preserved downstream: " + Arrays.toString(mapped));
    }
}
