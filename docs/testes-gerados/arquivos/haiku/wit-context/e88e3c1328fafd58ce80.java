/*
 * @test
 * @summary Verifies IntStream.peek null-action validation and observable element order
 * @run main IntPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testPeekObservesElementsInOrder();
    }

    static void testNullActionThrowsNullPointerException() {
        try {
            IntStream.of(1, 2).peek(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testPeekObservesElementsInOrder() {
        List<Integer> seen = new ArrayList<>();
        int sum = IntStream.of(1, 2, 3).peek(x -> seen.add(x)).sum();
        if (sum != 6) {
            throw new RuntimeException("unexpected sum: " + sum);
        }
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        if (!seen.equals(expected)) {
            throw new RuntimeException("unexpected peek order: " + seen);
        }
    }
}
