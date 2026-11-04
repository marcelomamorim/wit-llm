/*
 * @test
 * @summary Test the allMatch method of IntPipeline for various predicates.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchWithAllTruePredicate();
        testAllMatchWithSomeFalsePredicate();
        testAllMatchWithEmptyStream();
    }

    static void testAllMatchWithAllTruePredicate() {
        IntPredicate alwaysTrue = x -> true;
        boolean result = createStream(1, 2, 3).allMatch(alwaysTrue);
        if (!result) {
            throw new AssertionError("Expected true for all true predicate");
        }
    }

    static void testAllMatchWithSomeFalsePredicate() {
        IntPredicate isEven = x -> x % 2 == 0;
        boolean result = createStream(2, 4, 5).allMatch(isEven);
        if (result) {
            throw new AssertionError("Expected false when some elements do not match predicate");
        }
    }

    static void testAllMatchWithEmptyStream() {
        IntPredicate anyPredicate = x -> false;
        boolean result = createStream().allMatch(anyPredicate);
        if (!result) {
            throw new AssertionError("Expected true for empty stream");
        }
    }

    static IntPipeline<Integer> createStream(int... values) {
        return IntPipeline.from(java.util.Spliterators.spliterator(values, 0));
    }
}
