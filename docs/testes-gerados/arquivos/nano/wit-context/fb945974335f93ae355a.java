/*
 * @test
 * @summary Test for ReferencePipeline.limit method, covering exception path for invalid input.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testLimitWithNegativeMaxSize();
        testLimitWithZeroMaxSize();
        testLimitWithPositiveMaxSize();
    }

    static void testLimitWithNegativeMaxSize() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            ReferencePipeline.limit(stream, -1);
            System.err.println("Failed: Expected IllegalArgumentException for maxSize = -1");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!e.getMessage().equals("-1")) {
                System.err.println("Failed: Exception message mismatch for maxSize = -1");
                System.exit(1);
            }
        }
    }

    static void testLimitWithZeroMaxSize() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            ReferencePipeline.limit(stream, 0);
            System.err.println("Failed: Expected IllegalArgumentException for maxSize = 0");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!e.getMessage().equals("0")) {
                System.err.println("Failed: Exception message mismatch for maxSize = 0");
                System.exit(1);
            }
        }
    }

    static void testLimitWithPositiveMaxSize() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> limitedStream = ReferencePipeline.limit(stream, 2);
        Object[] result = limitedStream.toArray();
        if (result.length != 2 || !(result[0].equals(1)) || !(result[1].equals(2))) {
            System.err.println("Failed: limit did not produce expected output");
            System.exit(1);
        }
    }
}
