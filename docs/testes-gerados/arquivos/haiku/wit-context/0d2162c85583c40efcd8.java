/*
 * @test
 * @summary Witup checks for IntStream.reduce(IntBinaryOperator), which delegates to ReduceOps.makeInt
 * @run main ReduceOpsWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        OptionalInt empty = IntStream.empty().reduce((a, b) -> a + b);
        check(!empty.isPresent(), "empty stream must yield an empty OptionalInt");

        OptionalInt single = IntStream.of(7).reduce((a, b) -> a + b);
        check(single.isPresent() && single.getAsInt() == 7, "single element must be returned unchanged");

        OptionalInt sum = IntStream.of(1, 2, 3, 4).reduce((a, b) -> a + b);
        check(sum.isPresent() && sum.getAsInt() == 10, "sequential sum of 1..4 must be 10");

        OptionalInt max = IntStream.of(3, 9, 2).reduce(Math::max);
        check(max.isPresent() && max.getAsInt() == 9, "max reduction must be 9");

        OptionalInt par = IntStream.rangeClosed(1, 100).parallel().reduce((a, b) -> a + b);
        check(par.isPresent() && par.getAsInt() == 5050, "parallel sum of 1..100 must be 5050");

        boolean npe = false;
        try {
            IntStream.of(1, 2).reduce(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null operator must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
