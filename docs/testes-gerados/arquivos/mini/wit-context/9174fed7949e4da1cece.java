/*
 * @test
 * @summary Test IntStream.tryAdvance method for null action and normal behavior
 * @run main IntStreamWitupTest
 */

import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    static void testTryAdvanceNullAction() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceNormal() {
        final int[] consumed = new int[1];
        IntStream stream = IntStream.of(42);
        boolean advanced = stream.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        });
        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true when element is present");
        }
        if (consumed[0] != 42) {
            throw new RuntimeException("tryAdvance did not consume the expected value");
        }
    }
}
