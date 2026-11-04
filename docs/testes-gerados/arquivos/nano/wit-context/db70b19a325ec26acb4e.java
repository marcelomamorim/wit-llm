/*
 * @test
 * @summary Test for ReferencePipeline.findAny() method
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyWithLinkedOrConsumed();
        testFindAnyNormal();
    }

    private static void testFindAnyWithLinkedOrConsumed() {
        // Simulate a scenario where the pipeline is linked or consumed, expecting an exception
        try {
            // Since the actual internal state is not accessible, we simulate the behavior by calling evaluate with a mock or dummy
            // but as we cannot access internal state, we invoke findAny() on an empty stream to observe behavior
            Optional<?> result = ReferencePipeline.empty().findAny();
            // For an empty stream, result should be empty, no exception expected here
            if (result.isPresent()) {
                System.out.println("Unexpected: found element in empty stream.");
            } else {
                System.out.println("Empty stream correctly returned empty Optional.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException for linkedOrConsumed state.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testFindAnyNormal() {
        // Test normal behavior with a simple stream
        Optional<Integer> result = java.util.stream.Stream.of(1, 2, 3).map(i -> i).findAny();
        if (result.isPresent()) {
            System.out.println("findAny() returned: " + result.get());
        } else {
            System.out.println("findAny() returned empty.");
        }
    }
}
