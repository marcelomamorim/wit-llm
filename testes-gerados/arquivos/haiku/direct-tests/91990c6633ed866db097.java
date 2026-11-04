/*
 * @test
 * @summary Verifies the public Stream.reduce(BinaryOperator) behavior that delegates to ReferencePipeline.reduce
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        Optional<Integer> empty = Stream.<Integer>empty().reduce((a, b) -> a + b);
        check(!empty.isPresent(), "reduce on empty stream must return empty Optional");

        Optional<String> single = Stream.of("x").reduce((a, b) -> a + b);
        check(single.isPresent(), "reduce on single-element stream must be present");
        check("x".equals(single.get()), "single element must be returned unchanged");

        Optional<Integer> sum = Stream.of(1, 2, 3, 4).reduce((a, b) -> a + b);
        check(sum.isPresent(), "sum result must be present");
        check(sum.get() == 10, "sum of 1..4 must be 10, was " + sum.get());

        Optional<String> concat = Stream.of("a", "b", "c").reduce((a, b) -> a + b);
        check("abc".equals(concat.orElse(null)), "sequential reduce must preserve encounter order");

        Optional<Integer> max = Stream.of(3, 9, 2).reduce((a, b) -> a > b ? a : b);
        check(max.isPresent() && max.get() == 9, "max reduction must return 9");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
