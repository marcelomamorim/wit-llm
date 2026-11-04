/*
 * @test
 * @summary Tests the filter method of LongPipeline for various predicates.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;
import java.util.function.LongPredicate;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithAllMatchPredicate();
        testFilterWithNoneMatchPredicate();
        testFilterWithSomeMatchPredicate();
        testFilterWithNullPredicate();
    }

    static void testFilterWithAllMatchPredicate() {
        LongStream stream = LongStream.of(1, 2, 3, 4, 5);
        LongPredicate predicate = t -> t > 0;
        LongStream result = stream.filter(predicate);
        long[] expected = {1, 2, 3, 4, 5};
        long[] actual = result.toArray();
        assert arraysEqual(expected, actual) : "All elements should pass the filter";
    }

    static void testFilterWithNoneMatchPredicate() {
        LongStream stream = LongStream.of(1, 2, 3, 4, 5);
        LongPredicate predicate = t -> t > 10;
        LongStream result = stream.filter(predicate);
        long[] expected = {};
        long[] actual = result.toArray();
        assert arraysEqual(expected, actual) : "No elements should pass the filter";
    }

    static void testFilterWithSomeMatchPredicate() {
        LongStream stream = LongStream.of(1, 2, 3, 4, 5);
        LongPredicate predicate = t -> t % 2 == 0;
        LongStream result = stream.filter(predicate);
        long[] expected = {2, 4};
        long[] actual = result.toArray();
        assert arraysEqual(expected, actual) : "Only even elements should pass the filter";
    }

    static void testFilterWithNullPredicate() {
        try {
            LongStream stream = LongStream.of(1, 2, 3);
            stream.filter(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static boolean arraysEqual(long[] a, long[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }
}
