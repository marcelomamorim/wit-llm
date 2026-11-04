/*
 * @test
 * @summary Tests for ReferencePipeline.toArray method
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.ReferencePipeline;
import java.util.function.IntFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayWithEmptyStream();
        testToArrayWithSingleElement();
        testToArrayWithMultipleElements();
        testToArrayWithNullGenerator();
    }

    static void testToArrayWithEmptyStream() {
        ReferencePipeline<Integer> emptyStream = ReferencePipeline.empty();
        Integer[] result = emptyStream.toArray(Integer[]::new);
        assert result != null : "Result should not be null";
        assert result.length == 0 : "Array length should be 0";
    }

    static void testToArrayWithSingleElement() {
        ReferencePipeline<Integer> stream = ReferencePipeline.of(java.util.Arrays.asList(42).stream());
        Integer[] result = stream.toArray(Integer[]::new);
        assert result != null : "Result should not be null";
        assert result.length == 1 : "Array length should be 1";
        assert result[0] == 42 : "Element should be 42";
    }

    static void testToArrayWithMultipleElements() {
        ReferencePipeline<Integer> stream = ReferencePipeline.of(java.util.Arrays.asList(1, 2, 3).stream());
        Integer[] result = stream.toArray(Integer[]::new);
        assert result != null : "Result should not be null";
        assert result.length == 3 : "Array length should be 3";
        assert result[0] == 1 && result[1] == 2 && result[2] == 3 : "Elements should be 1, 2, 3";
    }

    static void testToArrayWithNullGenerator() {
        try {
            ReferencePipeline<Integer> stream = ReferencePipeline.of(java.util.Arrays.asList(1, 2).stream());
            stream.toArray(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
