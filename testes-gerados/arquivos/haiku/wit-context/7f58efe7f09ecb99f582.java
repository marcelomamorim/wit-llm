/*
 * @test
 * @summary Verifies public Stream.peek behavior (null action NPE, lazy evaluation, element order) backed by ReferencePipeline.peek
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testPeekIsLazy();
        testPeekObservesElementsInOrder();
    }

    static void testNullActionThrowsNpe() {
        boolean thrown = false;
        try {
            Stream.of("a").peek(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("peek(null) should throw NullPointerException");
        }
    }

    static void testPeekIsLazy() {
        List<Integer> seen = new ArrayList<>();
        Stream<Integer> s = Stream.of(1, 2).peek(seen::add);
        if (!seen.isEmpty()) {
            throw new AssertionError("peek must not run before a terminal operation");
        }
        List<Integer> result = s.collect(Collectors.toList());
        if (!result.equals(Arrays.asList(1, 2))) {
            throw new AssertionError("unexpected result: " + result);
        }
    }

    static void testPeekObservesElementsInOrder() {
        List<Integer> seen = new ArrayList<>();
        List<Integer> result = Stream.of(1, 2, 3)
                .peek(seen::add)
                .collect(Collectors.toList());
        if (!seen.equals(Arrays.asList(1, 2, 3))) {
            throw new AssertionError("peek observed: " + seen);
        }
        if (!result.equals(Arrays.asList(1, 2, 3))) {
            throw new AssertionError("result changed: " + result);
        }
    }
}
