/*
 * @test
 * @summary Exercises ReduceOps.makeRef(BinaryOperator) through the public Stream.reduce(BinaryOperator) API
 * @run main ReduceOpsWitupTest
 */
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceOpsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Optional<Integer> empty = Stream.<Integer>empty().reduce((a, b) -> a + b);
        check(!empty.isPresent(), "empty stream must yield Optional.empty");

        Optional<String> single = Stream.of("x").reduce((a, b) -> a + b);
        check(single.isPresent() && "x".equals(single.get()), "single element must be returned as-is");

        Optional<String> ordered = Stream.of("a", "b", "c").reduce((a, b) -> a + b);
        check("abc".equals(ordered.orElse(null)), "sequential reduction must preserve encounter order");

        Optional<Integer> parallel = IntStream.rangeClosed(1, 1000).boxed().parallel().reduce((a, b) -> a + b);
        check(parallel.isPresent() && parallel.get() == 500500, "parallel reduction must combine partial results");

        try {
            Stream.of("a").reduce((BinaryOperator<String>) null);
            throw new RuntimeException("null operator must be rejected");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(operator)
        }
    }
}
