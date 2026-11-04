/*
 * @test
 * @summary Test for IntPipeline's mapToObj method to ensure correct behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.Stream;
import java.util.Objects;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapToObjWithValidMapper();
        testMapToObjWithNullMapper();
    }

    static void testMapToObjWithValidMapper() {
        IntPipeline pipeline = IntPipeline.of(
            () -> java.util.Spliterators.spliterator(new int[]{1, 2, 3}, java.util.Spliterator.ORDERED),
            java.util.Spliterator.ORDERED, false);
        Stream<String> resultStream = pipeline.mapToObj(i -> "Number: " + i);
        String[] result = resultStream.toArray(String[]::new);
        assert result.length == 3 : "Expected 3 elements";
        assert "Number: 1".equals(result[0]) : "First element mismatch";
        assert "Number: 2".equals(result[1]) : "Second element mismatch";
        assert "Number: 3".equals(result[2]) : "Third element mismatch";
    }

    static void testMapToObjWithNullMapper() {
        IntPipeline pipeline = IntPipeline.of(
            () -> java.util.Spliterators.spliterator(new int[]{4, 5, 6}, java.util.Spliterator.ORDERED),
            java.util.Spliterator.ORDERED, false);
        try {
            pipeline.mapToObj(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
