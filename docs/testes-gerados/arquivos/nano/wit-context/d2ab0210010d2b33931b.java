/*
 * @test
 * @summary Tests for IntPipeline.skip(long n)
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    static void testSkipNegative() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            IntPipeline result = (IntPipeline) stream.skip(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for skip(-1)");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for skip(-1)");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    static void testSkipZero() {
        IntStream stream = IntStream.of(4, 5, 6);
        IntStream result = stream.skip(0);
        int[] expected = {4, 5, 6};
        int[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("PASS: skip(0) returns original stream elements");
        } else {
            System.out.println("FAIL: skip(0) did not return original stream elements");
        }
    }

    static void testSkipPositive() {
        IntStream stream = IntStream.of(7, 8, 9);
        IntStream skipped = stream.skip(2);
        int[] expected = {9};
        int[] actual = skipped.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("PASS: skip(2) skips first two elements");
        } else {
            System.out.println("FAIL: skip(2) did not skip correctly");
        }
    }
}
