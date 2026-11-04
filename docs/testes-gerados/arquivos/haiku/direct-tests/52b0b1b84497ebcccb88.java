/*
 * @test
 * @summary Verifies the observable behavior of the IntStream.Builder implementation backed by Streams.accept(int)
 * @run main StreamsWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamsWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testEmptyBuilder() {
        int[] result = IntStream.builder().build().toArray();
        check(result.length == 0, "empty builder must produce empty stream");
    }

    private static void testSingleElement() {
        IntStream.Builder b = IntStream.builder();
        b.accept(7);
        int[] result = b.build().toArray();
        check(Arrays.equals(result, new int[]{7}), "single element must be preserved: " + Arrays.toString(result));
    }

    private static void testMultipleElementsOrder() {
        IntStream.Builder b = IntStream.builder();
        b.accept(1);
        b.accept(2);
        b.accept(3);
        b.accept(4);
        int[] result = b.build().toArray();
        check(Arrays.equals(result, new int[]{1, 2, 3, 4}),
              "elements must keep insertion order: " + Arrays.toString(result));
    }

    private static void testBoundaryValues() {
        IntStream.Builder b = IntStream.builder();
        b.accept(Integer.MIN_VALUE);
        b.accept(-1);
        b.accept(0);
        b.accept(Integer.MAX_VALUE);
        int[] result = b.build().toArray();
        check(Arrays.equals(result, new int[]{Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE}),
              "boundary values must be preserved: " + Arrays.toString(result));
    }

    private static void testAcceptAfterBuildThrows() {
        IntStream.Builder b = IntStream.builder();
        b.accept(5);
        b.build();
        try {
            b.accept(6);
            throw new RuntimeException("accept after build must throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testEmptyBuilder();
        testSingleElement();
        testMultipleElementsOrder();
        testBoundaryValues();
        testAcceptAfterBuildThrows();
    }
}
