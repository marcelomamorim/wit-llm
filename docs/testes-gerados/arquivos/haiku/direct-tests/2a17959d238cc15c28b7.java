/*
 * @test
 * @summary Verifies that sized int streams materialized via toArray preserve every element in order, exercising the fixed-size int node builder path behind Nodes.
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class NodesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRangeToArray() {
        int[] result = IntStream.range(0, 1000).toArray();
        check(result.length == 1000, "unexpected length: " + result.length);
        for (int i = 0; i < result.length; i++) {
            check(result[i] == i, "element " + i + " was " + result[i]);
        }
    }

    static void testOfToArrayPreservesOrder() {
        int[] expected = {5, -3, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 42};
        int[] result = IntStream.of(expected).toArray();
        check(Arrays.equals(expected, result),
              "array mismatch: " + Arrays.toString(result));
    }

    static void testEmptySizedStream() {
        int[] result = IntStream.of().toArray();
        check(result.length == 0, "expected empty array, length " + result.length);
    }

    static void testSingleElement() {
        int[] result = IntStream.of(7).toArray();
        check(result.length == 1 && result[0] == 7,
              "unexpected single-element result: " + Arrays.toString(result));
    }

    public static void main(String[] args) {
        testRangeToArray();
        testOfToArrayPreservesOrder();
        testEmptySizedStream();
        testSingleElement();
    }
}
