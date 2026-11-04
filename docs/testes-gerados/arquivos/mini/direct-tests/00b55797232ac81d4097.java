/*
 * @test
 * @summary Test IntPipeline.mapToObj(IntFunction) for basic functionality and null handling
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.function.IntFunction;
import java.util.Objects;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testMapToObjBasic();
        testMapToObjNullMapper();
    }

    private static void testMapToObjBasic() {
        IntStream ints = IntStream.of(1, 2, 3);
        IntFunction<String> mapper = new IntFunction<String>() {
            public String apply(int value) {
                return "Val" + value;
            }
        };
        Stream<String> result = ints.mapToObj(mapper);
        Object[] arr = result.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected 3 elements but got " + arr.length);
        }
        if (!"Val1".equals(arr[0]) || !"Val2".equals(arr[1]) || !"Val3".equals(arr[2])) {
            throw new AssertionError("Mapped values do not match expected results");
        }
    }

    private static void testMapToObjNullMapper() {
        IntStream ints = IntStream.of(1);
        try {
            ints.mapToObj(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
