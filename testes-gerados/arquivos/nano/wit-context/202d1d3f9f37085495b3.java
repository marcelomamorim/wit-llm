/*
 * @test
 * @summary Tests for LongPipeline methods, focusing on exception paths and behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.util.function.LongToIntFunction;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntWithNullMapper();
        testMapToIntWithValidMapper();
    }

    public static void testMapToIntWithNullMapper() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        try {
            stream.mapToInt(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null mapper");
        }
    }

    public static void testMapToIntWithValidMapper() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        LongToIntFunction mapper = t -> (int) t;
        IntStream resultStream = stream.mapToInt(mapper);
        int[] expected = {1, 2, 3};
        int index = 0;
        boolean success = true;
        for (int val : resultStream.toArray()) {
            if (val != expected[index]) {
                success = false;
                break;
            }
            index++;
        }
        if (success && index == expected.length) {
            System.out.println("mapToInt with valid mapper produces expected output");
        } else {
            System.err.println("Unexpected output from mapToInt with valid mapper");
            System.exit(1);
        }
    }
}
