/*
 * @test
 * @summary Public behavior of ReferencePipeline.map: element transformation, null mapper rejection, and laziness on empty streams
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapTransformsElementsInOrder();
        testMapChangesElementType();
        testNullMapperThrowsNullPointerException();
        testMapperNotInvokedForEmptyStream();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testMapTransformsElementsInOrder() {
        List<Integer> result = Stream.of(1, 2, 3)
                .map(i -> i * 2)
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList(2, 4, 6)), "unexpected result " + result);
    }

    static void testMapChangesElementType() {
        List<String> result = Stream.of(1, 2, 3)
                .map(i -> "v" + i)
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList("v1", "v2", "v3")), "unexpected result " + result);
    }

    static void testNullMapperThrowsNullPointerException() {
        try {
            Stream.of("a").map(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testMapperNotInvokedForEmptyStream() {
        int[] calls = {0};
        List<String> result = Stream.<String>empty()
                .map(s -> {
                    calls[0]++;
                    return s;
                })
                .collect(Collectors.toList());
        check(result.isEmpty(), "expected empty result but was " + result);
        check(calls[0] == 0, "mapper invoked " + calls[0] + " times for empty stream");
    }
}
