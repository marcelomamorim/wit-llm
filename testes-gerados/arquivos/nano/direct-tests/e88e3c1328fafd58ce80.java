/*
 * @test
 * @summary Test for IntPipeline.peek method to ensure it correctly invokes the provided IntConsumer.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testPeekInvokesAction();
    }

    static void testPeekInvokesAction() {
        final boolean[] invoked = {false};
        IntConsumer consumer = (int t) -> {
            invoked[0] = true;
            // Optionally, check the value of t if needed
        };
        IntStream stream = IntStream.of(1, 2, 3).peek(consumer);
        stream.forEach(i -> {});
        assert invoked[0] : "peek action was not invoked";
    }
}
