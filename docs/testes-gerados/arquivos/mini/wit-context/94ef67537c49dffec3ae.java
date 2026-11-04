/*
 * @test
 * @summary Test java.util.stream.Stream.tryAdvance(Consumer) for null action and normal usage
 * @run main StreamWitupTest
 */

import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.Objects;

public class StreamWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    static void testTryAdvanceNullAction() {
        Stream<String> stream = Stream.of("a", "b");
        try {
            stream.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceNormal() {
        Stream<String> stream = Stream.of("x");
        final StringBuilder sb = new StringBuilder();
        boolean advanced = stream.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced) {
            throw new Error("tryAdvance should return true when element is present");
        }
        if (!"x".equals(sb.toString())) {
            throw new Error("Consumer did not receive expected element");
        }
        // Subsequent tryAdvance should return false as stream is exhausted
        boolean secondAdvance = stream.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called on exhausted stream");
            }
        });
        if (secondAdvance) {
            throw new Error("tryAdvance should return false when no elements remain");
        }
    }
}
