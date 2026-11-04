/*
 * @test
 * @summary Test for IntPipeline.allMatch to observe behavior with valid and invalid predicates.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchWithValidPredicate();
        testAllMatchWithNullPredicate();
    }

    static void testAllMatchWithValidPredicate() {
        int[] data = {1, 2, 3, 4, 5};
        boolean result = IntPipeline.of(data, false)
            .allMatch(i -> i > 0);
        System.out.println("allMatch with predicate i > 0: " + result);
        // Expect true, as all elements are > 0
    }

    static void testAllMatchWithNullPredicate() {
        int[] data = {1, 2, 3};
        try {
            boolean result = IntPipeline.of(data, false)
                .allMatch(null);
            System.out.println("allMatch with null predicate: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null predicate.");
        }
        // Expect NullPointerException, as predicate is null
    }
}
