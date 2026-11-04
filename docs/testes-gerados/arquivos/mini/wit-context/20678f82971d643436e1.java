/*
 * @test
 * @summary Test java.util.stream.Stream.concat method for null inputs and normal concatenation
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import java.util.Objects;

public class StreamWitupTest {
    public static void main(String[] args) {
        testConcatNullA();
        testConcatNullB();
        testConcatNormal();
    }

    private static void testConcatNullA() {
        Stream<String> s = Stream.of("a");
        try {
            Stream.concat(null, s);
            throw new RuntimeException("Expected NullPointerException for null first stream");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConcatNullB() {
        Stream<String> s = Stream.of("a");
        try {
            Stream.concat(s, null);
            throw new RuntimeException("Expected NullPointerException for null second stream");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConcatNormal() {
        Stream<String> s1 = Stream.of("x", "y");
        Stream<String> s2 = Stream.of("z");
        Stream<String> concatStream = Stream.concat(s1, s2);

        // Collect elements to array to verify order and content
        Object[] result = concatStream.toArray();

        if (result.length != 3) {
            throw new RuntimeException("Concatenated stream length expected 3 but was " + result.length);
        }
        if (!"x".equals(result[0]) || !"y".equals(result[1]) || !"z".equals(result[2])) {
            throw new RuntimeException("Concatenated stream elements not in expected order");
        }
    }
}
