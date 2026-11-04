/*
 * @test
 * @summary Verifies LongStream.limit behavior through the public API, including the IllegalArgumentException for negative sizes
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        check(Arrays.equals(LongStream.of(1L, 2L, 3L, 4L, 5L).limit(2L).toArray(),
                            new long[]{1L, 2L}), "limit(2) should keep the first two elements");

        check(LongStream.of(1L, 2L, 3L).limit(0L).toArray().length == 0,
              "limit(0) should produce an empty stream");

        check(Arrays.equals(LongStream.of(1L, 2L, 3L).limit(10L).toArray(),
                            new long[]{1L, 2L, 3L}), "limit larger than size should keep all elements");

        check(Arrays.equals(LongStream.of(1L, 2L, 3L, 4L).limit(3L).limit(2L).toArray(),
                            new long[]{1L, 2L}), "chained limit should apply the smaller bound");

        check(LongStream.empty().limit(5L).count() == 0L,
              "limit on an empty stream should be empty");

        try {
            LongStream.of(1L).limit(-1L);
            throw new RuntimeException("limit(-1) should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("-1".equals(e.getMessage()),
                  "exception message should be the negative size, was: " + e.getMessage());
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
