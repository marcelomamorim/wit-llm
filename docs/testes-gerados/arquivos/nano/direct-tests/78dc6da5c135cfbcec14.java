/*
 * @test
 * @summary Test StackWalker forEach method with a simple consumer
 * @run main StackWalkerWitupTest
 */
import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.function.Consumer;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    static void testForEachWithValidConsumer() {
        StackWalker walker = StackWalker.getInstance();
        final boolean[] called = {false};
        Consumer<StackFrame> consumer = frame -> {
            called[0] = true;
            // Optionally, check some properties of frame
            if (frame != null) {
                String methodName = frame.getMethodName();
                // We expect method name to be non-null
                if (methodName == null) {
                    throw new AssertionError("Method name should not be null");
                }
            }
        };
        walker.forEach(consumer);
        if (!called[0]) {
            throw new AssertionError("Consumer was not called");
        }
    }

    static void testForEachWithNullConsumer() {
        try {
            StackWalker walker = StackWalker.getInstance();
            walker.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
