/*
 * @test
 * @summary Test the skip method of LongPipeline for various inputs.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;
import java.util.stream.SliceOps;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
        testSkipLarge();
    }

    static void testSkipNegative() {
        LongStream stream = LongStream.of(1, 2, 3);
        try {
            stream.skip(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative skip");
        }
    }

    static void testSkipZero() {
        LongStream stream = LongStream.of(4, 5, 6);
        LongStream result = stream.skip(0);
        long[] expected = {4, 5, 6};
        long[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("PASS: skip(0) returns original stream elements");
        } else {
            System.out.println("FAIL: skip(0) did not return original stream elements");
        }
    }

    static void testSkipPositive() {
        LongStream stream = LongStream.of(7, 8, 9, 10);
        LongStream result = stream.skip(2);
        long[] expected = {9, 10};
        long[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("PASS: skip(2) skips first two elements");
        } else {
            System.out.println("FAIL: skip(2) did not skip correctly");
        }
    }

    static void testSkipLarge() {
        LongStream stream = LongStream.of(11, 12);
        LongStream result = stream.skip(10);
        long[] expected = {};
        long[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("PASS: skip larger than stream size results in empty stream");
        } else {
            System.out.println("FAIL: skip larger than stream size did not produce empty stream");
        }
    }
}
