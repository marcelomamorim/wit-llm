/*
 * @test
 * @summary Test for DoublePipeline.peek method to ensure it correctly invokes the provided DoubleConsumer.
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoubleStream;
import java.util.stream.DoublePipeline;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        testPeekInvokesAction();
    }

    public static void testPeekInvokesAction() {
        final boolean[] invoked = {false};
        DoubleConsumer consumer = (double t) -> {
            invoked[0] = true;
            // Optionally, check the value of t if needed
        };
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        DoubleStream resultStream = stream.peek(consumer);
        // Trigger terminal operation to execute peek
        double sum = resultStream.sum();
        // Verify that the consumer was invoked during the terminal operation
        if (!invoked[0]) {
            throw new AssertionError("peek did not invoke the consumer as expected.");
        }
        // Additional check: sum should be correct
        if (sum != 6.0) {
            throw new AssertionError("Sum mismatch: expected 6.0, got " + sum);
        }
    }
}