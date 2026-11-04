/*
 * @test
 * @summary Verify observable behavior of IntStream.reduce(IntBinaryOperator), which delegates to IntPipeline.reduce
 * @run main IntPipelineWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        OptionalInt empty = IntStream.empty().reduce(Integer::sum);
        check(!empty.isPresent(), "reduce on empty stream must return an empty OptionalInt");

        check(IntStream.of(1, 2, 3, 4).reduce(Integer::sum).getAsInt() == 10, "sum of 1..4 must be 10");

        check(IntStream.of(42).reduce(Integer::sum).getAsInt() == 42, "single element must be returned unchanged");

        check(IntStream.of(3, 9, 4).reduce(Math::max).getAsInt() == 9, "max reduction must return 9");

        check(IntStream.of(10, 3, 2).reduce((a, b) -> a - b).getAsInt() == 5,
              "sequential reduction ((10-3)-2) must be 5");

        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
