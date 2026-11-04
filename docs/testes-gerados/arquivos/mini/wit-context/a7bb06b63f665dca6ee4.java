/*
 * @test
 * @summary Test java.util.stream.Stream.forEachRemaining(Consumer) for null and normal behavior
 * @run main StreamWitupTest
 */

import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.Objects;

public class StreamWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        Stream<String> stream = Stream.of("a", "b");
        try {
            stream.forEachRemaining(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        final StringBuilder sb = new StringBuilder();
        Stream<String> stream = Stream.of("x", "y", "z");
        stream.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        String result = sb.toString();
        if (!"xyz".equals(result)) {
            throw new Error("Expected concatenated string 'xyz' but got '" + result + "'");
        }
    }
}
