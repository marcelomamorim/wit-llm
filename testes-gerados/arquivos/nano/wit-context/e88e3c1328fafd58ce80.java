/*
 * @test
 * @summary Tests the behavior of IntPipeline.peek method, especially null handling.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithNullAction();
        testPeekWithValidAction();
    }

    private static void testPeekWithNullAction() {
        boolean exceptionThrown = false;
        try {
            IntStream stream = IntStream.of(1, 2, 3);
            stream.peek(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to peek")
        }
    }

    private static void testPeekWithValidAction() {
        final StringBuilder log = new StringBuilder();
        IntConsumer consumer = (int t) -> log.append(t).append(",");
        IntStream stream = IntStream.of(4, 5, 6);
        IntStream resultStream = stream.peek(consumer);
        StringBuilder resultLog = new StringBuilder();
        resultStream.forEach(t -> resultLog.append(t).append(","));
        if (!"4,5,6,".equals(resultLog.toString())) {
            throw new AssertionError("peek did not invoke consumer correctly")
        }
    }
}
