/*
 * @test
 * @summary Tests the filter method of IntPipeline for correct behavior.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testFilterWithPredicateTrue();
        testFilterWithPredicateFalse();
        testFilterWithNullPredicate();
    }

    static void testFilterWithPredicateTrue() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntPredicate predicate = t -> true;
        IntStream filtered = stream.filter(predicate);
        int[] result = filtered.toArray();
        assert result.length == 5 : "Expected 5 elements";
        for (int i = 0; i < result.length; i++) {
            assert result[i] == i + 1 : "Unexpected element: " + result[i];
        }
    }

    static void testFilterWithPredicateFalse() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntPredicate predicate = t -> false;
        IntStream filtered = stream.filter(predicate);
        int[] result = filtered.toArray();
        assert result.length == 0 : "Expected 0 elements";
    }

    static void testFilterWithNullPredicate() {
        boolean exceptionThrown = false;
        try {
            IntStream stream = IntStream.of(1, 2, 3);
            stream.filter(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when predicate is null";
    }
}
