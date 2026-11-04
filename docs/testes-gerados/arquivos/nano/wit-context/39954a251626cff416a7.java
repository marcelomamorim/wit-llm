/*
 * @test
 * @summary Tests for DoublePipeline.peek method behavior.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithNullAction();
        testPeekWithValidAction();
    }

    private static void testPeekWithNullAction() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        try {
            stream.peek(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testPeekWithValidAction() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer consumer = d -> sb.append(d).append(",");
        DoubleStream resultStream = stream.peek(consumer);
        resultStream.forEach(d -> {});
        String result = sb.toString();
        if (!result.equals("1.0,2.0,3.0,")) {
            throw new AssertionError("Unexpected peek side-effect: " + result);
        }
    }
}
