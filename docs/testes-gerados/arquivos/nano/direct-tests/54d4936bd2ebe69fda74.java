/*
 * @test    @summary Test for DoublePipeline.mapToInt method
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.IntStream;
import java.util.function.DoubleToIntFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntWithNonNullMapper();
        testMapToIntWithNullMapper();
    }

    static void testMapToIntWithNonNullMapper() {
        DoubleToIntFunction mapper = d -> (int) (d * 10);
        DoublePipeline.DoublePipelineHelper pipeline = new DoublePipeline.DoublePipelineHelper();
        // Simulate a pipeline with a single element
        DoublePipeline pipelineInstance = new DoublePipeline(() -> java.util.Spliterators.spliterator(new double[]{1.5}, java.util.Spliterator.SIZED), 0, false) {
            @Override
            public IntStream mapToInt(DoubleToIntFunction mapper) {
                return super.mapToInt(mapper);
            }
        };
        IntStream resultStream = pipelineInstance.mapToInt(mapper);
        int[] result = resultStream.toArray();
        assert result.length == 1 : "Expected one element";
        assert result[0] == 15 : "Expected 15, got " + result[0];
    }

    static void testMapToIntWithNullMapper() {
        try {
            DoublePipeline.DoublePipelineHelper pipeline = new DoublePipeline.DoublePipelineHelper();
            DoublePipeline pipelineInstance = new DoublePipeline(() -> java.util.Spliterators.spliterator(new double[]{2.0}, java.util.Spliterator.SIZED), 0, false) {
                @Override
                public IntStream mapToInt(DoubleToIntFunction mapper) {
                    return super.mapToInt(mapper);
                }
            };
            pipelineInstance.mapToInt(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
