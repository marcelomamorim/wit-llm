/*
 * @test
 * @summary Verify LongPipeline.forEach visits elements in order, rejects reuse of a consumed stream and rejects a null action
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        long[] sum = new long[1];
        long[] count = new long[1];
        LongStream.rangeClosed(1, 5).forEach(v -> { sum[0] += v; count[0]++; });
        if (sum[0] != 15L || count[0] != 5L) {
            throw new AssertionError("unexpected sum or count: " + sum[0] + ", " + count[0]);
        }

        StringBuilder sb = new StringBuilder();
        LongStream.of(3L, 1L, 2L).forEach(v -> sb.append(v));
        if (!"312".equals(sb.toString())) {
            throw new AssertionError("unexpected order: " + sb);
        }

        LongStream consumed = LongStream.of(1L, 2L);
        consumed.forEach(v -> { });
        boolean threw = false;
        try {
            consumed.forEach(v -> { });
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("expected IllegalStateException on reused stream");
        }

        boolean npe = false;
        try {
            LongStream.of(1L).forEach(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("expected NullPointerException for null action");
        }
    }
}
