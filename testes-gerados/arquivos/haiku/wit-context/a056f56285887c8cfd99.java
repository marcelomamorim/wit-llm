/*
 * @test
 * @summary Behavioral tests for the LongPipeline map stage (accept path) through the public LongStream API
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        check(Arrays.equals(LongStream.of(1L, 2L, 3L).map(x -> x * 2L).toArray(), new long[]{2L, 4L, 6L}), 1);
        check(Arrays.equals(LongStream.empty().map(x -> x + 1L).toArray(), new long[0]), 2);
        check(LongStream.of(1L, 2L).map(x -> x * 10L).map(x -> x + 1L).sum() == 32L, 3);

        boolean thrown = false;
        try {
            LongStream.of(1L).map(x -> { throw new IllegalArgumentException(); }).sum();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, 4);
    }

    private static void check(boolean condition, int id) {
        if (!condition) {
            throw new RuntimeException(Integer.toString(id));
        }
    }
}
