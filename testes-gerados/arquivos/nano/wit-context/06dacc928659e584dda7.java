/*
 * @test
 * @summary Tests for LongPipeline.skip(long n)
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.SliceOps;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        LongStream stream = LongStream.of(1, 2, 3);
        try {
            stream.skip(-1);
            System.out.println("testSkipNegative: FAILED - no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testSkipNegative: PASSED");
        } catch (Exception e) {
            System.out.println("testSkipNegative: FAILED - wrong exception: " + e);
        }
    }

    private static void testSkipZero() {
        LongStream stream = LongStream.of(4, 5, 6);
        LongStream result = stream.skip(0);
        long[] expected = {4, 5, 6};
        long[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("testSkipZero: PASSED");
        } else {
            System.out.println("testSkipZero: FAILED - unexpected result");
        }
    }

    private static void testSkipPositive() {
        LongStream stream = LongStream.of(7, 8, 9);
        LongStream result = stream.skip(2);
        long[] expected = {9};
        long[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("testSkipPositive: PASSED");
        } else {
            System.out.println("testSkipPositive: FAILED - unexpected result");
        }
    }
}
