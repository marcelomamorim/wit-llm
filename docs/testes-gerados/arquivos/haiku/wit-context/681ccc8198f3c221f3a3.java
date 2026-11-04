/*
 * @test
 * @summary Observable behavior of LongStream.sorted(), which reaches SortedOps.OfLong.opWrapSink
 * @run main SortedOpsWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class SortedOpsWitupTest {
    public static void main(String[] args) {
        check(Arrays.equals(LongStream.of(3L, 1L, 2L).sorted().toArray(), new long[]{1L, 2L, 3L}), "sized sorted");
        check(Arrays.equals(LongStream.of(3L, 1L, 2L).filter(x -> true).sorted().toArray(), new long[]{1L, 2L, 3L}), "unsized sorted");
        check(Arrays.equals(LongStream.of(-1L, Long.MAX_VALUE, Long.MIN_VALUE, 0L).sorted().toArray(), new long[]{Long.MIN_VALUE, -1L, 0L, Long.MAX_VALUE}), "extremes");
        check(LongStream.empty().sorted().count() == 0L, "empty");
        check(Arrays.equals(LongStream.of(5L, 4L).sorted().sorted().toArray(), new long[]{4L, 5L}), "double sorted");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
