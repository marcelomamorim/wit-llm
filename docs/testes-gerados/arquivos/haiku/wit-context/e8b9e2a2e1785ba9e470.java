/*
 * @test
 * @summary Verify DoublePipeline.filter rejects a null predicate eagerly and filters elements correctly through the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNullPredicateThrowsNpeEagerly();
        testFilterKeepsMatchingElements();
        testFilterWithAlwaysFalseYieldsEmpty();
    }

    static void testNullPredicateThrowsNpeEagerly() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0, 2.0).filter(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null predicate in filter");
        }
    }

    static void testFilterKeepsMatchingElements() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0, 4.0)
                .filter(d -> d > 2.0)
                .toArray();
        double[] expected = {3.0, 4.0};
        if (!Arrays.equals(expected, result)) {
            throw new AssertionError("Unexpected filter result: " + Arrays.toString(result));
        }
    }

    static void testFilterWithAlwaysFalseYieldsEmpty() {
        long count = DoubleStream.of(1.0, 2.0, 3.0)
                .filter(d -> false)
                .count();
        if (count != 0L) {
            throw new AssertionError("Expected empty stream, count=" + count);
        }
    }
}
