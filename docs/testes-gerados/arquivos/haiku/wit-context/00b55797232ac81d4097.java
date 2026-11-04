/*
 * @test
 * @summary Witup-derived checks for IntPipeline.mapToObj null-mapper NPE and basic mapping behaviour via public IntStream API
 * @run main IntPipelineWitupTest
 */
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNullPointerException();
        testMapToObjProducesExpectedElements();
        testMapToObjOnEmptyStreamProducesEmptyResult();
    }

    static void testNullMapperThrowsNullPointerException() {
        boolean thrown = false;
        try {
            IntStream.of(1, 2, 3).mapToObj((IntFunction<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mapper");
        }
    }

    static void testMapToObjProducesExpectedElements() {
        List<String> result = IntStream.of(1, 2, 3)
                .mapToObj(i -> "x" + i)
                .collect(Collectors.toList());
        List<String> expected = Arrays.asList("x1", "x2", "x3");
        if (!expected.equals(result)) {
            throw new RuntimeException("Unexpected mapToObj result: " + result);
        }
    }

    static void testMapToObjOnEmptyStreamProducesEmptyResult() {
        List<String> result = IntStream.empty()
                .mapToObj(i -> "x" + i)
                .collect(Collectors.toList());
        if (!result.isEmpty()) {
            throw new RuntimeException("Expected empty result but got: " + result);
        }
    }
}
