/*
 * @test
 * @summary Test ReferencePipeline.anyMatch behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testAnyMatchWithMatchingElement();
        testAnyMatchWithNoMatchingElement();
        testAnyMatchWithEmptyStream();
        testAnyMatchWithNullPredicate();
    }

    private static void testAnyMatchWithMatchingElement() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Predicate<String> predicate = s -> "b".equals(s);
        boolean result = stream.anyMatch(predicate);
        if (!result) {
            throw new AssertionError("Expected anyMatch to return true when element matches predicate");
        }
    }

    private static void testAnyMatchWithNoMatchingElement() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Predicate<String> predicate = s -> "z".equals(s);
        boolean result = stream.anyMatch(predicate);
        if (result) {
            throw new AssertionError("Expected anyMatch to return false when no element matches predicate");
        }
    }

    private static void testAnyMatchWithEmptyStream() {
        Stream<String> stream = Stream.empty();
        Predicate<String> predicate = s -> true;
        boolean result = stream.anyMatch(predicate);
        if (result) {
            throw new AssertionError("Expected anyMatch to return false on empty stream");
        }
    }

    private static void testAnyMatchWithNullPredicate() {
        Stream<String> stream = Stream.of("a");
        try {
            stream.anyMatch(null);
            throw new AssertionError("Expected NullPointerException when predicate is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
