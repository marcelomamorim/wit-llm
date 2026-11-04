/*
 * @test
 * @summary Tests for java.util.stream.Stream methods, focusing on concat behavior.
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testConcatNullA();
        testConcatNullB();
        testConcatValidStreams();
    }

    private static void testConcatNullA() {
        try {
            Stream<Integer> b = Stream.of(1, 2, 3);
            Stream.concat(null, b);
            throw new AssertionError("Expected NullPointerException for null a");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConcatNullB() {
        try {
            Stream<Integer> a = Stream.of(4, 5, 6);
            Stream.concat(a, null);
            throw new AssertionError("Expected NullPointerException for null b");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConcatValidStreams() {
        Stream<Integer> a = Stream.of(1, 2);
        Stream<Integer> b = Stream.of(3, 4);
        Stream<Integer> result = Stream.concat(a, b);
        Integer[] expected = {1, 2, 3, 4};
        Integer[] actual = result.toArray(Integer[]::new);
        if (!java.util.Arrays.equals(expected, actual)) {
            throw new AssertionError("Concatenated stream does not match expected: " + java.util.Arrays.toString(actual));
        }
    }
}
