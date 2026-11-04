/*
 * @test
 * @summary Test for DoublePipeline.mapToObj method to ensure correct stream transformation.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.Stream;
import java.util.function.DoubleFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapToObjWithValidFunction();
        testMapToObjWithNullFunction();
    }

    static void testMapToObjWithValidFunction() {
        DoubleFunction<String> func = d -> "Value: " + d;
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public Stream<Double> stream() {
                return Stream.of(1.0, 2.0, 3.0);
            }
        };
        Stream<String> resultStream = pipeline.mapToObj(func);
        String[] results = resultStream.toArray(String[]::new);
        assert results.length == 3 : "Expected 3 elements";
        assert results[0].equals("Value: 1.0") : "First element mismatch";
        assert results[1].equals("Value: 2.0") : "Second element mismatch";
        assert results[2].equals("Value: 3.0") : "Third element mismatch";
    }

    static void testMapToObjWithNullFunction() {
        try {
            DoubleFunction<String> nullFunc = null;
            DoublePipeline pipeline = new DoublePipeline() {
                @Override
                public Stream<Double> stream() {
                    return Stream.of(1.0);
                }
            };
            pipeline.mapToObj(nullFunc);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
