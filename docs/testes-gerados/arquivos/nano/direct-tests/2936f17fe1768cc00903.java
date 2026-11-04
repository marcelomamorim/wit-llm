/*
 * @test
 * @summary Tests the noneMatch method of DoublePipeline for various predicates.
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNoneMatchAllMatch();
        testNoneMatchNoneMatch();
        testNoneMatchSomeMatch();
        testNoneMatchEmptyStream();
    }

    static void testNoneMatchAllMatch() {
        DoublePredicate predicate = d -> d > 0;
        boolean result = DoublePipeline.noneMatch(predicate);
        if (result != false) {
            throw new AssertionError("Expected false when all elements match predicate");
        }
    }

    static void testNoneMatchNoneMatch() {
        DoublePredicate predicate = d -> d < 0;
        boolean result = DoublePipeline.noneMatch(predicate);
        if (result != true) {
            throw new AssertionError("Expected true when no elements match predicate");
        }
    }

    static void testNoneMatchSomeMatch() {
        DoublePredicate predicate = d -> d == 0;
        boolean result = DoublePipeline.noneMatch(predicate);
        if (result != true) {
            throw new AssertionError("Expected true when some elements match predicate");
        }
    }

    static void testNoneMatchEmptyStream() {
        // Create an empty DoubleStream
        boolean result = DoublePipeline.noneMatch(d -> true);
        if (result != true) {
            throw new AssertionError("Expected true for empty stream");
        }
    }
}
