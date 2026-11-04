/*
 * @test
 * @summary Test for ReferencePipeline.forEach method to observe behavior and exception paths.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithEmptyStream();
        testForEachWithSingleElement();
    }

    static void testForEachWithNullAction() {
        try {
            ReferencePipeline<Object> stream = createEmptyStream();
            stream.forEach(null);
            System.out.println("testForEachWithNullAction: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testForEachWithNullAction: PASSED");
        } catch (Throwable t) {
            System.out.println("testForEachWithNullAction: FAILED - Unexpected exception: " + t);
        }
    }

    static void testForEachWithEmptyStream() {
        try {
            ReferencePipeline<Object> stream = createEmptyStream();
            final boolean[] called = {false};
            stream.forEach(e -> called[0] = true);
            if (called[0]) {
                System.out.println("testForEachWithEmptyStream: PASSED");
            } else {
                System.out.println("testForEachWithEmptyStream: FAILED - Action not called");
            }
        } catch (Throwable t) {
            System.out.println("testForEachWithEmptyStream: FAILED - Exception: " + t);
        }
    }

    static void testForEachWithSingleElement() {
        try {
            ReferencePipeline<Integer> stream = createSingleElementStream(42);
            final int[] sum = {0};
            stream.forEach(e -> sum[0] += e);
            if (sum[0] == 42) {
                System.out.println("testForEachWithSingleElement: PASSED");
            } else {
                System.out.println("testForEachWithSingleElement: FAILED - Sum mismatch");
            }
        } catch (Throwable t) {
            System.out.println("testForEachWithSingleElement: FAILED - Exception: " + t);
        }
    }

    static <T> ReferencePipeline<T, T> createEmptyStream() {
        return ReferencePipeline.from(java.util.Collections.emptyList().stream());
    }

    static <T> ReferencePipeline<T, T> createSingleElementStream(T element) {
        return ReferencePipeline.from(java.util.Collections.singletonList(element).stream());
    }
}
