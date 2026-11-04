/*
 * @test
 * @summary Test ReferencePipeline.skip(long) for valid and invalid inputs
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            Stream.of("a", "b", "c")::spliterator, 0, false) {
            @Override
            public Stream<String> skip(long n) {
                return super.skip(n);
            }
        };

        try {
            pipeline.skip(-1);
            throw new Error("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!e.getMessage().equals("-1")) {
                throw new Error("Exception message mismatch: " + e.getMessage());
            }
        }
    }

    private static void testSkipZero() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            Stream.of("a", "b", "c")::spliterator, 0, false) {
            @Override
            public Stream<String> skip(long n) {
                return super.skip(n);
            }
        };

        Stream<String> result = pipeline.skip(0);
        if (result != pipeline) {
            throw new Error("skip(0) should return the same stream instance");
        }
    }

    private static void testSkipPositive() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            Stream.of("a", "b", "c")::spliterator, 0, false) {
            @Override
            public Stream<String> skip(long n) {
                return super.skip(n);
            }
        };

        Stream<String> skipped = pipeline.skip(2);
        Object[] arr = skipped.toArray();
        if (arr.length != 1 || !"c".equals(arr[0])) {
            throw new Error("skip(2) did not skip correctly, got: " + java.util.Arrays.toString(arr));
        }
    }
}
