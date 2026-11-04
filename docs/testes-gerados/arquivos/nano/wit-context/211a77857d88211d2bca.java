/*
 * @test
 * @summary Test for ReferencePipeline.skip(long n) method.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    static void testSkipNegative() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Stream<Integer> stream = list.stream();
        try {
            stream.skip(-1).count();
            System.err.println("Failed: Expected IllegalArgumentException for skip(-1)");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: skip(-1) throws IllegalArgumentException");
        }
    }

    static void testSkipZero() {
        List<Integer> list = Arrays.asList(4, 5, 6);
        Stream<Integer> stream = list.stream();
        long count = stream.skip(0).count();
        if (count == 3) {
            System.out.println("Passed: skip(0) returns all elements");
        } else {
            System.err.println("Failed: skip(0) did not return all elements");
        }
    }

    static void testSkipPositive() {
        List<Integer> list = Arrays.asList(7, 8, 9, 10);
        Stream<Integer> stream = list.stream();
        long count = stream.skip(2).count();
        if (count == 2) {
            System.out.println("Passed: skip(2) skips first two elements");
        } else {
            System.err.println("Failed: skip(2) did not skip correctly");
        }
    }
}
