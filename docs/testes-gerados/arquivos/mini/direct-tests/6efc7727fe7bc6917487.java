/*
 * @test
 * @summary Test ReferencePipeline.mapToInt with a simple mapping function
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.function.ToIntFunction;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntBasic();
        testMapToIntNullMapper();
    }

    static void testMapToIntBasic() {
        Stream<String> stream = Stream.of("a", "bb", "ccc");
        ToIntFunction<String> lengthMapper = new ToIntFunction<String>() {
            public int applyAsInt(String s) {
                return s.length();
            }
        };
        IntStream intStream = ((ReferencePipeline<String, String>)stream).mapToInt(lengthMapper);
        int[] results = intStream.toArray();
        if (results.length != 3 || results[0] != 1 || results[1] != 2 || results[2] != 3) {
            throw new AssertionError("mapToInt did not produce expected lengths");
        }
    }

    static void testMapToIntNullMapper() {
        Stream<String> stream = Stream.of("a");
        try {
            ((ReferencePipeline<String, String>)stream).mapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
