/*
 * @test
 * @summary Test StackWalker.StackFrame.getMethodType() throws UnsupportedOperationException
 * @run main StackWalkerWitupTest
 */

import java.lang.invoke.MethodType;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        // Create a StackWalker with RETAIN_CLASS_REFERENCE to get StackFrame with getMethodType
        java.lang.StackWalker walker = java.lang.StackWalker.getInstance(java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE);

        // Use walk to get a Stream of StackFrame
        java.util.stream.Stream<java.lang.StackWalker.StackFrame> frames = walker.walk(s -> s.limit(1));

        java.lang.StackWalker.StackFrame frame = frames.findFirst().orElse(null);
        if (frame == null) {
            throw new AssertionError("No stack frame available");
        }

        // Test that getMethodType throws UnsupportedOperationException
        boolean threw = false;
        try {
            MethodType mt = frame.getMethodType();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from getMethodType()");
        }

        System.out.println("Test passed: getMethodType() throws UnsupportedOperationException as expected.");
    }
}
