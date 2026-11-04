/*
 * @test
 * @summary Test LongPipeline.peek method for correct behavior.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testPeekCallsAction();
        testPeekNullAction();
    }

    static void testPeekCallsAction() {
        final boolean[] called = {false};
        LongConsumer action = t -> {
            called[0] = true;
            if (t != 42L) {
                throw new AssertionError("Unexpected value: " + t);
            }
        };
        LongStream stream = LongStream.of(42L);
        LongStream resultStream = stream.peek(action);
        resultStream.forEach(t -> {});
        if (!called[0]) {
            throw new AssertionError("Action was not called");
        }
    }

    static void testPeekNullAction() {
        try {
            LongStream stream = LongStream.of(1L);
            stream.peek(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
