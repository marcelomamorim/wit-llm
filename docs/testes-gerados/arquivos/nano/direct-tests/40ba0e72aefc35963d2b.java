/*
 * @test
 * @summary Tests for IntPipeline.mapToLong method to ensure correct behavior.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.stream.LongStream;
import java.util.function.IntToLongFunction;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithValidFunction();
        testMapToLongWithNullFunction();
    }

    static void testMapToLongWithValidFunction() {
        IntToLongFunction func = t -> t * 10L;
        IntPipeline pipeline = IntPipeline.of(
            () -> java.util.Spliterators.spliterator(new int[]{1, 2, 3}, java.util.Spliterator.ORDERED),
            0,
            false
        );
        LongStream resultStream = pipeline.mapToLong(func);
        long[] result = resultStream.toArray();
        assert result.length == 3 : "Expected length 3";
        assert result[0] == 10L : "Expected 10";
        assert result[1] == 20L : "Expected 20";
        assert result[2] == 30L : "Expected 30";
    }

    static void testMapToLongWithNullFunction() {
        boolean threw = false;
        try {
            IntPipeline.of(
                () -> java.util.Spliterators.spliterator(new int[]{1, 2, 3}, java.util.Spliterator.ORDERED),
                0,
                false
            ).mapToLong(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw : "Expected NullPointerException when passing null";
    }
}
