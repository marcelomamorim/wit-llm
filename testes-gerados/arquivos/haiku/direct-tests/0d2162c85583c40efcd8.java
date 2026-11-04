/*
 * @test
 * @summary Checks public IntStream.reduce(IntBinaryOperator) behavior, which reaches ReduceOps.makeInt
 * @run main ReduceOpsWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ReduceOpsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        OptionalInt empty = IntStream.empty().reduce((a, b) -> a + b);
        check(!empty.isPresent(), "empty stream must yield OptionalInt.empty()");

        OptionalInt single = IntStream.of(42).reduce((a, b) -> a + b);
        check(single.isPresent() && single.getAsInt() == 42,
              "single element must be returned as-is, got " + single);

        OptionalInt sum = IntStream.of(1, 2, 3, 4).reduce((a, b) -> a + b);
        check(sum.isPresent() && sum.getAsInt() == 10,
              "sum of 1..4 must be 10, got " + sum);

        OptionalInt order = IntStream.of(10, 3, 2).reduce((a, b) -> a - b);
        check(order.isPresent() && order.getAsInt() == 5,
              "sequential left fold of 10,3,2 with subtraction must be 5, got " + order);

        OptionalInt parallelSum = IntStream.rangeClosed(1, 1000).parallel().reduce((a, b) -> a + b);
        check(parallelSum.isPresent() && parallelSum.getAsInt() == 500500,
              "parallel sum of 1..1000 must be 500500, got " + parallelSum);

        OptionalInt parallelEmpty = IntStream.empty().parallel().reduce((a, b) -> a + b);
        check(!parallelEmpty.isPresent(), "parallel empty stream must yield OptionalInt.empty()");

        boolean npe = false;
        try {
            IntStream.of(1, 2).reduce(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null operator must cause NullPointerException");
    }
}
