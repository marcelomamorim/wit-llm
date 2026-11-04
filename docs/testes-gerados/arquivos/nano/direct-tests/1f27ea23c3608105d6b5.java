/*
 * @test
 * @summary Test for ReferencePipeline.anyMatch(Predicate) method.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.Predicate;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchWithMatchingPredicate();
        testAnyMatchWithNonMatchingPredicate();
        testAnyMatchWithEmptyStream();
    }

    static void testAnyMatchWithMatchingPredicate() {
        String[] data = {"apple", "banana", "cherry"};
        boolean result = ReferencePipeline.of(java.util.Arrays.asList(data).stream())
            .anyMatch(s -> s.startsWith("b"));
        if (!result) {
            throw new AssertionError("Expected true when predicate matches an element");
        }
    }

    static void testAnyMatchWithNonMatchingPredicate() {
        String[] data = {"apple", "banana", "cherry"};
        boolean result = ReferencePipeline.of(java.util.Arrays.asList(data).stream())
            .anyMatch(s -> s.startsWith("z"));
        if (result) {
            throw new AssertionError("Expected false when predicate does not match any element");
        }
    }

    static void testAnyMatchWithEmptyStream() {
        boolean result = ReferencePipeline.of(java.util.stream.Stream.<String>empty())
            .anyMatch(s -> true);
        if (result) {
            throw new AssertionError("Expected false for empty stream");
        }
    }
}
