/*
 * @test
 * @summary Test for IntPipeline.filter method to verify behavior with null predicate.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithValidPredicate();
    }

    private static void testFilterWithNullPredicate() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.filter(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null predicate");
        }
    }

    private static void testFilterWithValidPredicate() {
        IntStream stream = IntStream.of(1, 2, 3, 4);
        IntPredicate predicate = t -> t % 2 == 0;
        int[] result = stream.filter(predicate).toArray();
        if (result.length != 2 || result[0] != 2 || result[1] != 4) {
            System.err.println("Filter with valid predicate failed: " + java.util.Arrays.toString(result));
            System.exit(1);
        } else {
            System.out.println("Filter with valid predicate succeeded: " + java.util.Arrays.toString(result));
        }
    }
}
