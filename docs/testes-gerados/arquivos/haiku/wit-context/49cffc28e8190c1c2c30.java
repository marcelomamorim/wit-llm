/*
 * @test
 * @summary Observable LongStream.toArray behavior that exercises Nodes builders through the public API
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class NodesWitupTest {
    public static void main(String[] args) {
        long[] expected = {1L, 2L, 3L};
        long[] actual = Arrays.stream(expected).toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError("Arrays.stream(long[]).toArray() mismatch: " + Arrays.toString(actual));
        }

        long[] ranged = LongStream.rangeClosed(1L, 3L).toArray();
        if (!Arrays.equals(expected, ranged)) {
            throw new AssertionError("rangeClosed toArray mismatch: " + Arrays.toString(ranged));
        }

        long[] of = LongStream.of(4L, 5L).toArray();
        if (!Arrays.equals(new long[] {4L, 5L}, of)) {
            throw new AssertionError("LongStream.of toArray mismatch: " + Arrays.toString(of));
        }

        long count = LongStream.range(0L, 1000L).toArray().length;
        if (count != 1000L) {
            throw new AssertionError("Unexpected sized array length: " + count);
        }
    }
}
