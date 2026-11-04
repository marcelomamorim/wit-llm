/*
 * @test
 * @summary Test DoublePipeline.anyMatch with various predicates
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        testAnyMatchTrue();
        testAnyMatchFalse();
        testAnyMatchEmpty();
    }

    static void testAnyMatchTrue() {
        DoublePredicate predicate = d -> d > 0;
        double[] data = {1.0, 2.0, 3.0};
        boolean result = DoublePipeline.of(data).anyMatch(predicate);
        if (!result) {
            throw new AssertionError("Expected true for data with positive numbers");
        }
    }

    static void testAnyMatchFalse() {
        DoublePredicate predicate = d -> d < 0;
        double[] data = {1.0, 2.0, 3.0};
        boolean result = DoublePipeline.of(data).anyMatch(predicate);
        if (result) {
            throw new AssertionError("Expected false for data with no negative numbers");
        }
    }

    static void testAnyMatchEmpty() {
        DoublePredicate predicate = d -> true;
        double[] data = {};
        boolean result = DoublePipeline.of(data).anyMatch(predicate);
        if (result) {
            throw new AssertionError("Expected false for empty data");
        }
    }
}
