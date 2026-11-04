/*
 * @test
 * @summary Verify LongStream.filter null-predicate rejection and filtering behavior through public API
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNullPredicateThrowsNpe();
        testFilterKeepsMatchingElementsInOrder();
        testFilterWithNoMatchesYieldsEmptyArray();
        testFilterAllMatchYieldsAllElements();
        testFilterOnEmptyStreamYieldsEmptyArray();
    }

    static void testNullPredicateThrowsNpe() {
        boolean thrown = false;
        try {
            LongStream.of(1L, 2L, 3L).filter(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null predicate");
        }
    }

    static void testFilterKeepsMatchingElementsInOrder() {
        long[] result = LongStream.rangeClosed(1, 6)
                .filter(x -> x % 2 == 0)
                .toArray();
        check(Arrays.equals(result, new long[] {2L, 4L, 6L}), "expected [2, 4, 6] but got " + Arrays.toString(result));
    }

    static void testFilterWithNoMatchesYieldsEmptyArray() {
        long[] result = LongStream.of(1L, 3L, 5L)
                .filter(x -> x % 2 == 0)
                .toArray();
        check(result.length == 0, "expected empty array but got " + Arrays.toString(result));
    }

    static void testFilterAllMatchYieldsAllElements() {
        long[] result = LongStream.of(7L, 8L, 9L)
                .filter(x -> true)
                .toArray();
        check(Arrays.equals(result, new long[] {7L, 8L, 9L}), "expected all elements but got " + Arrays.toString(result));
    }

    static void testFilterOnEmptyStreamYieldsEmptyArray() {
        long[] result = LongStream.empty()
                .filter(x -> x > 0)
                .toArray();
        check(result.length == 0, "expected empty array for empty source");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
