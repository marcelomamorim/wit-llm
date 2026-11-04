/*
 * @test
 * @summary Verify that ReferencePipeline.filter rejects a null predicate and filters elements correctly
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullPredicateThrowsNPE();
        testFilterKeepsMatchingElements();
        testFilterOnEmptyStream();
    }

    static void testNullPredicateThrowsNPE() {
        try {
            Stream.of(1, 2, 3).filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFilterKeepsMatchingElements() {
        List<Integer> result = Stream.of(1, 2, 3, 4)
                .filter(x -> x % 2 == 1)
                .collect(Collectors.toList());
        List<Integer> expected = Arrays.asList(1, 3);
        if (!expected.equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testFilterOnEmptyStream() {
        List<Integer> result = Stream.<Integer>empty()
                .filter(x -> true)
                .collect(Collectors.toList());
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty result but got: " + result);
        }
    }
}
