/*
 * @test
 * @summary Verify IntPipeline.min() returns the minimum element observable through the public IntStream API
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        OptionalInt r1 = IntStream.of(3, 1, 2).min();
        check(r1.isPresent(), "min of non-empty stream must be present");
        check(r1.getAsInt() == 1, "min of {3,1,2} must be 1");

        OptionalInt r2 = IntStream.empty().min();
        check(!r2.isPresent(), "min of empty stream must be empty");

        check(IntStream.of(-5).min().getAsInt() == -5, "min of single element must be that element");

        check(IntStream.of(Integer.MAX_VALUE, Integer.MIN_VALUE).min().getAsInt() == Integer.MIN_VALUE,
              "min must handle Integer.MIN_VALUE");

        check(IntStream.of(7, 7, 7).min().getAsInt() == 7, "min of duplicates must be the repeated value");

        check(IntStream.of(-1, -10, -3).min().getAsInt() == -10, "min of negative values must be -10");

        check(IntStream.range(0, 10).filter(i -> i >= 5).min().getAsInt() == 5,
              "min after filter must reflect filtered elements");

        check(IntStream.range(0, 1000).parallel().min().getAsInt() == 0,
              "parallel min must equal sequential min");

        check(IntStream.range(0, 1000).map(i -> 999 - i).min().getAsInt() == 0,
              "min of mapped reversed range must be 0");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
