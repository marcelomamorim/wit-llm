/*
 * @test
 * @summary Test IntPipeline.anyMatch with various predicates
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testAnyMatchTruePredicate();
        testAnyMatchFalsePredicate();
        testAnyMatchEmptyStream();
    }

    static void testAnyMatchTruePredicate() {
        IntPredicate predicate = x -> x > 0;
        int[] data = {1, 2, 3};
        IntPipeline.OfInt stream = IntPipeline.of(data, 0, data.length, false);
        boolean result = stream.anyMatch(predicate);
        if (!result) {
            throw new AssertionError("Expected true for anyMatch with predicate x > 0");
        }
    }

    static void testAnyMatchFalsePredicate() {
        IntPredicate predicate = x -> x < 0;
        int[] data = {1, 2, 3};
        IntPipeline.OfInt stream = IntPipeline.of(data, 0, data.length, false);
        boolean result = stream.anyMatch(predicate);
        if (result) {
            throw new AssertionError("Expected false for anyMatch with predicate x < 0");
        }
    }

    static void testAnyMatchEmptyStream() {
        int[] data = {};
        IntPipeline.OfInt stream = IntPipeline.of(data, 0, data.length, false);
        boolean result = stream.anyMatch(x -> true);
        if (result) {
            throw new AssertionError("Expected false for anyMatch on empty stream");
        }
    }
}
