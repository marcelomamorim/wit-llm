/*
 * @test    @summary Test for java.util.stream.Stream:forEachRemaining method
 * @run main StreamWitupTest
 */
import java.util.stream.Stream;
import java.util.function.Consumer;

public class StreamWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyStream();
    }

    static void testForEachRemainingWithElements() {
        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Stream.of("a", "b", "c");
        stream.forEachRemaining(s -> sb.append(s));
        assert sb.toString().equals("abc") : "Expected 'abc', but got '" + sb.toString() + "'";
    }

    static void testForEachRemainingWithEmptyStream() {
        StringBuilder sb = new StringBuilder();
        Stream<String> emptyStream = Stream.<String>empty();
        emptyStream.forEachRemaining(s -> sb.append(s));
        assert sb.toString().isEmpty() : "Expected empty string, but got '" + sb.toString() + "'";
    }
}
