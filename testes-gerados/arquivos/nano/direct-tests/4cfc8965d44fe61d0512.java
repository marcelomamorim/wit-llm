/*
 * @test
 * @summary Tests for ReferencePipeline.flatMap method
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNonNullMapper();
        testFlatMapWithNullMapper();
        testFlatMapWithEmptyStream();
        testFlatMapWithNullStreamResult();
    }

    static void testFlatMapWithNonNullMapper() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<String> result = stream.flatMap(i -> Stream.of("a" + i, "b" + i));
        StringBuilder sb = new StringBuilder();
        result.forEach(s -> sb.append(s).append(","));
        assert sb.toString().equals("a1,b1,a2,b2,a3,b3,") : "Failed: testFlatMapWithNonNullMapper";
    }

    static void testFlatMapWithNullMapper() {
        Stream<Integer> stream = Stream.of(1, 2);
        try {
            stream.flatMap(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFlatMapWithEmptyStream() {
        Stream<Integer> stream = Stream.empty();
        Stream<String> result = stream.flatMap(i -> Stream.of("x" + i));
        long count = result.count();
        assert count == 0 : "Failed: testFlatMapWithEmptyStream";
    }

    static void testFlatMapWithNullStreamResult() {
        Stream<Integer> stream = Stream.of(1);
        Stream<String> result = stream.flatMap(i -> null);
        try {
            result.count();
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
