/*
 * @test
 * @summary Verifies IntStream.sorted() through public API: small sized input is sorted, and a sized input whose size reaches the max array size is rejected with IllegalArgumentException from SortedOps begin(long size).
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testSortedSmallSizedInput();
        testSortedOversizedSizedInputThrows();
    }

    static void testSortedSmallSizedInput() {
        int[] result = IntStream.of(3, 1, 2).sorted().toArray();
        int[] expected = {1, 2, 3};
        if (!Arrays.equals(expected, result)) {
            throw new AssertionError("Expected " + Arrays.toString(expected)
                    + " but got " + Arrays.toString(result));
        }
    }

    static void testSortedOversizedSizedInputThrows() {
        boolean thrown = false;
        try {
            IntStream.range(0, Integer.MAX_VALUE)
                     .sorted()
                     .forEach(i -> { });
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for oversized sized input");
        }
    }
}
