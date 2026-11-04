/*
 * @test
 * @summary Test IntPipeline.filter method for null predicate and filtering behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntPredicate;
import java.util.Objects;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFilterNullPredicate();
        testFilterBehavior();
    }

    private static void testFilterNullPredicate() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.filter(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testFilterBehavior() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntPredicate even = new IntPredicate() {
            public boolean test(int value) {
                return value % 2 == 0;
            }
        };
        int[] results = stream.filter(even).toArray();
        if (results.length != 2 || results[0] != 2 || results[1] != 4) {
            throw new RuntimeException("Filter did not correctly filter even numbers");
        }
    }
}
