/*
 * @test
 * @summary Checks public Stream.reduce(BinaryOperator) behavior, which delegates to ReferencePipeline.reduce
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        Optional<Integer> sum = Stream.of(1, 2, 3).reduce(Integer::sum);
        check(sum.isPresent() && sum.get() == 6, "reduce of 1,2,3 should be 6");

        Optional<Integer> empty = Stream.<Integer>empty().reduce(Integer::sum);
        check(!empty.isPresent(), "reduce of empty stream should be Optional.empty");

        Stream<Integer> reused = Stream.of(1, 2);
        reused.reduce(Integer::sum);
        boolean thrown = false;
        try {
            reused.reduce(Integer::sum);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream should throw IllegalStateException");

        boolean npe = false;
        try {
            Stream.of(1).reduce((BinaryOperator<Integer>) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null accumulator should throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
