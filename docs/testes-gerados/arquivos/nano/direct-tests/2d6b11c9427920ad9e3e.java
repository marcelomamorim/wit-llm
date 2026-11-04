/*
 * @test
 * @summary Tests the noneMatch method of IntPipeline for various predicates.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNoneMatchAllMatch();
        testNoneMatchSomeMatch();
        testNoneMatchNoneMatch();
    }

    static void testNoneMatchAllMatch() {
        IntPredicate predicate = x -> x < 100;
        int[] data = {1, 2, 3, 4, 5};
        boolean result = createStream(data).noneMatch(predicate);
        if (result != false) {
            throw new AssertionError("Expected false when all match predicate");
        }
    }

    static void testNoneMatchSomeMatch() {
        IntPredicate predicate = x -> x > 3;
        int[] data = {1, 2, 3, 4, 5};
        boolean result = createStream(data).noneMatch(predicate);
        if (result != false) {
            throw new AssertionError("Expected false when some match predicate");
        }
    }

    static void testNoneMatchNoneMatch() {
        IntPredicate predicate = x -> x > 10;
        int[] data = {1, 2, 3, 4, 5};
        boolean result = createStream(data).noneMatch(predicate);
        if (result != true) {
            throw new AssertionError("Expected true when none match predicate");
        }
    }

    static IntPipeline.OfInt createStream(int[] data) {
        return IntPipeline.fromSpliterator(java.util.Spliterators.spliterator(data, 0), false);
    }
}
