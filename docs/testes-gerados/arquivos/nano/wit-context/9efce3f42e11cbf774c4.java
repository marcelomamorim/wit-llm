/*
 * @test
 * @summary Test for ReferencePipeline.max method to observe behavior with various comparators.
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.Comparator;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMaxWithNullComparator();
        testMaxWithEmptyStream();
        testMaxWithSingleElement();
        testMaxWithMultipleElements();
    }

    static void testMaxWithNullComparator() {
        try {
            // Creating a stream with elements
            Optional<Integer> result = ReferencePipeline.of(java.util.stream.Stream.of(1, 2, 3))
                .max(null);
            // Expecting a NullPointerException as per JDK 11+28 behavior
            System.out.println("testMaxWithNullComparator: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testMaxWithNullComparator: Passed - NullPointerException caught");
        } catch (Exception e) {
            System.out.println("testMaxWithNullComparator: Failed - unexpected exception: " + e);
        }
    }

    static void testMaxWithEmptyStream() {
        Optional<Integer> result = java.util.stream.Stream.<Integer>empty()
            .collect(java.util.stream.Collectors.toList())
            .stream()
            .max(Comparator.naturalOrder());
        if (!result.isPresent()) {
            System.out.println("testMaxWithEmptyStream: Passed - empty stream returns Optional.empty()");
        } else {
            System.out.println("testMaxWithEmptyStream: Failed - expected Optional.empty(), got: " + result);
        }
    }

    static void testMaxWithSingleElement() {
        Optional<Integer> result = java.util.stream.Stream.of(42)
            .max(Comparator.naturalOrder());
        if (result.isPresent() && result.get() == 42) {
            System.out.println("testMaxWithSingleElement: Passed");
        } else {
            System.out.println("testMaxWithSingleElement: Failed - got: " + result);
        }
    }

    static void testMaxWithMultipleElements() {
        Optional<Integer> result = java.util.stream.Stream.of(1, 3, 2)
            .max(Comparator.naturalOrder());
        if (result.isPresent() && result.get() == 3) {
            System.out.println("testMaxWithMultipleElements: Passed");
        } else {
            System.out.println("testMaxWithMultipleElements: Failed - got: " + result);
        }
    }
}
