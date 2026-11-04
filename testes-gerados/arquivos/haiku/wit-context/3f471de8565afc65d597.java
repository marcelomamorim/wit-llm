/*
 * @test
 * @summary Observable sorting behavior of sized LongStream, which reaches the SortedOps sized long sink begin path
 * @run main SortedOpsWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        checkEquals(new long[]{1L, 2L, 3L}, LongStream.of(3L, 1L, 2L).sorted().toArray(), "small sized stream");
        checkEquals(new long[0], LongStream.empty().sorted().toArray(), "empty sized stream");
        checkEquals(new long[]{Long.MIN_VALUE, -1L, 0L, Long.MAX_VALUE},
                LongStream.of(Long.MAX_VALUE, 0L, Long.MIN_VALUE, -1L).sorted().toArray(), "boundary values");
        checkEquals(LongStream.rangeClosed(1, 100).toArray(),
                LongStream.rangeClosed(1, 100).map(i -> 101 - i).sorted().toArray(), "reversed range");
    }

    static void checkEquals(long[] expected, long[] actual, String label) {
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected result for " + label + ": " + Arrays.toString(actual));
        }
    }
}
