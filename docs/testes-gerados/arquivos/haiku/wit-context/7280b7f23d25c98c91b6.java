/*
 * @test
 * @summary Checks public LongStream.sum() behavior, including empty, overflow wrap-around, and reuse after consumption (IllegalStateException)
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        check(LongStream.empty().sum() == 0L, "empty stream sum must be 0");
        check(LongStream.rangeClosed(1, 10).sum() == 55L, "sum of 1..10 must be 55");
        check(Arrays.stream(new long[]{-3L, 5L, -2L}).sum() == 0L, "mixed-sign sum must be 0");
        check(LongStream.of(Long.MAX_VALUE, 1L).sum() == Long.MIN_VALUE, "overflow must wrap around");

        LongStream consumed = LongStream.of(1L, 2L);
        check(consumed.sum() == 3L, "first sum must be 3");
        expectIllegalState(() -> consumed.sum());

        LongStream consumedByCount = LongStream.of(4L, 5L);
        check(consumedByCount.count() == 2L, "count must be 2");
        expectIllegalState(() -> consumedByCount.sum());
    }

    private static void expectIllegalState(Runnable action) {
        try {
            action.run();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException was not thrown");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
