/*
 * @test
 * @summary Verifies LongStream.max() observable behavior, including reuse of a consumed stream (IllegalStateException)
 * @run main LongPipelineWitupTest
 */
import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        OptionalLong r = LongStream.of(3L, 1L, 2L).max();
        check(r.isPresent() && r.getAsLong() == 3L, "max of 3,1,2 should be 3");

        r = LongStream.of(-5L, -1L, -10L).max();
        check(r.isPresent() && r.getAsLong() == -1L, "max of negatives should be -1");

        r = LongStream.of(Long.MIN_VALUE).max();
        check(r.isPresent() && r.getAsLong() == Long.MIN_VALUE, "max of single MIN_VALUE");

        r = LongStream.of(Long.MAX_VALUE, 0L).max();
        check(r.isPresent() && r.getAsLong() == Long.MAX_VALUE, "max should handle MAX_VALUE");

        r = LongStream.empty().max();
        check(!r.isPresent(), "max of empty stream should be empty");

        LongStream consumed = LongStream.of(1L, 2L);
        consumed.max();
        boolean threw = false;
        try {
            consumed.max();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "reusing a consumed stream for max() should throw IllegalStateException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
