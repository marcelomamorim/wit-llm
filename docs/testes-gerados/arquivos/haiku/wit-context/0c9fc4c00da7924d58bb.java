/*
 * @test
 * @summary Verify IntStream.reduce(int, IntBinaryOperator) observable behavior, which delegates to ReduceOps.makeInt
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.IntStream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        int sum = IntStream.of(1, 2, 3, 4).reduce(0, (a, b) -> a + b);
        check(sum == 10, "sum expected 10 but was " + sum);

        int product = IntStream.of(2, 3, 4).reduce(1, (a, b) -> a * b);
        check(product == 24, "product expected 24 but was " + product);

        int empty = IntStream.empty().reduce(42, (a, b) -> a + b);
        check(empty == 42, "empty stream expected identity 42 but was " + empty);

        int parallel = IntStream.rangeClosed(1, 100).parallel().reduce(0, Integer::sum);
        check(parallel == 5050, "parallel sum expected 5050 but was " + parallel);

        boolean npe = false;
        try {
            IntStream.of(1).reduce(0, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null operator must cause NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
