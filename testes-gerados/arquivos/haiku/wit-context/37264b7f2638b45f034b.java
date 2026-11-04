/*
 * @test
 * @summary Behavioral checks for IntStream.map, which reaches the anonymous Sink accept(int) of IntPipeline
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapAppliesMapperToEachElement();
        testMapPreservesOrder();
        testMapperExceptionPropagates();
        testEmptyStreamDoesNotInvokeMapper();
    }

    static void testMapAppliesMapperToEachElement() {
        int sum = IntStream.of(1, 2, 3).map(x -> x * 2).sum();
        if (sum != 12) {
            throw new AssertionError("expected 12 but was " + sum);
        }
    }

    static void testMapPreservesOrder() {
        int[] result = IntStream.of(3, 1, 2).map(x -> x + 1).toArray();
        int[] expected = {4, 2, 3};
        if (!Arrays.equals(expected, result)) {
            throw new AssertionError("unexpected order: " + Arrays.toString(result));
        }
    }

    static void testMapperExceptionPropagates() {
        boolean thrown = false;
        try {
            IntStream.of(1).map(x -> {
                throw new IllegalStateException("boom");
            }).sum();
        } catch (IllegalStateException e) {
            thrown = "boom".equals(e.getMessage());
        }
        if (!thrown) {
            throw new AssertionError("mapper exception did not propagate");
        }
    }

    static void testEmptyStreamDoesNotInvokeMapper() {
        int[] calls = {0};
        long count = IntStream.empty().map(x -> {
            calls[0]++;
            return x;
        }).count();
        if (calls[0] != 0 || count != 0) {
            throw new AssertionError("calls=" + calls[0] + " count=" + count);
        }
    }
}
