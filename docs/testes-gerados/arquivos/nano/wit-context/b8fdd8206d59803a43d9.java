/*
 * @test
 * @summary Test for IntPipeline.map method to verify behavior with null argument.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapWithValidMapper();
    }

    private static void testMapWithNullMapper() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.map(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }

    private static void testMapWithValidMapper() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntUnaryOperator doubleMapper = (int x) -> x * 2;
        IntStream resultStream = stream.map(doubleMapper);
        int[] result = resultStream.toArray();
        int[] expected = {2, 4, 6};
        if (java.util.Arrays.equals(result, expected)) {
            System.out.println("Passed: map with valid mapper produces expected output");
        } else {
            System.err.println("Failed: map with valid mapper produces unexpected output: " + java.util.Arrays.toString(result));
            System.exit(1);
        }
    }
}
