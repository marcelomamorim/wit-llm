/*
 * @test
 * @summary Verifies LongStream.concat ordering, null argument checks, parallel flag and close handling
 * @run main LongStreamWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongStreamWitupTest {
    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) {
        testConcatPreservesOrder();
        testConcatParallelPreservesOrder();
        testConcatSum();
        testConcatEmptyStreams();
        testConcatNullFirstThrowsNpe();
        testConcatNullSecondThrowsNpe();
        testConcatParallelFlag();
        testConcatSequentialFlag();
        testConcatCloseRunsBothHandlers();

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s) in LongStreamWitupTest");
        }
    }

    static void testConcatPreservesOrder() {
        long[] result = LongStream.concat(LongStream.of(1L, 2L), LongStream.of(3L, 4L)).toArray();
        check(Arrays.equals(result, new long[] {1L, 2L, 3L, 4L}),
              "concat should preserve order: " + Arrays.toString(result));
    }

    static void testConcatParallelPreservesOrder() {
        long[] result = LongStream.concat(LongStream.of(1L, 2L).parallel(), LongStream.of(3L, 4L)).toArray();
        check(Arrays.equals(result, new long[] {1L, 2L, 3L, 4L}),
              "parallel concat should preserve encounter order: " + Arrays.toString(result));
    }

    static void testConcatSum() {
        long sum = LongStream.concat(LongStream.of(1L, 2L), LongStream.of(3L)).sum();
        check(sum == 6L, "sum of concatenation should be 6 but was " + sum);
    }

    static void testConcatEmptyStreams() {
        long count = LongStream.concat(LongStream.of(), LongStream.of()).count();
        check(count == 0L, "concat of empty streams should have count 0 but was " + count);
    }

    static void testConcatNullFirstThrowsNpe() {
        boolean npe = false;
        try {
            LongStream.concat(null, LongStream.of(1L));
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "concat(null, b) should throw NullPointerException");
    }

    static void testConcatNullSecondThrowsNpe() {
        boolean npe = false;
        try {
            LongStream.concat(LongStream.of(1L), null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "concat(a, null) should throw NullPointerException");
    }

    static void testConcatParallelFlag() {
        LongStream s = LongStream.concat(LongStream.of(1L).parallel(), LongStream.of(2L));
        check(s.isParallel(), "concat should be parallel when either input is parallel");
        s.close();
    }

    static void testConcatSequentialFlag() {
        LongStream s = LongStream.concat(LongStream.of(1L), LongStream.of(2L));
        check(!s.isParallel(), "concat of sequential streams should be sequential");
        s.close();
    }

    static void testConcatCloseRunsBothHandlers() {
        final int[] closes = {0};
        LongStream a = LongStream.of(1L).onClose(() -> closes[0]++);
        LongStream b = LongStream.of(2L).onClose(() -> closes[0]++);
        LongStream c = LongStream.concat(a, b);
        c.toArray();
        c.close();
        check(closes[0] == 2, "closing concatenated stream should run both close handlers, runs=" + closes[0]);
    }
}
