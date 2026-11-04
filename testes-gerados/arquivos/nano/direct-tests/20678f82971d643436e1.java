/*
 * @test
 * @summary Tests the static method Stream.concat for correctness and exception handling
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.Objects;

public class StreamWitupTest {

    public static void main(String[] args) {
        testConcatWithValidStreams();
        testConcatWithNullA();
        testConcatWithNullB();
        testConcatWithEmptyStreams();
    }

    static void testConcatWithValidStreams() {
        Stream<Integer> s1 = Stream.of(1, 2, 3);
        Stream<Integer> s2 = Stream.of(4, 5);
        Stream<Integer> result = Stream.concat(s1, s2);
        int sum = result.mapToInt(Integer::intValue).sum();
        assert sum == 15 : "Sum should be 15";
    }

    static void testConcatWithNullA() {
        try {
            Stream<Integer> b = Stream.of(1, 2);
            Stream.concat(null, b);
            assert false : "Expected NullPointerException for null a";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConcatWithNullB() {
        try {
            Stream<Integer> a = Stream.of(1, 2);
            Stream.concat(a, null);
            assert false : "Expected NullPointerException for null b";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConcatWithEmptyStreams() {
        Stream<Integer> empty1 = Stream.empty();
        Stream<Integer> empty2 = Stream.empty();
        Stream<Integer> result = Stream.concat(empty1, empty2);
        assert result.count() == 0 : "Resulting stream should be empty";
    }
}
