/*
 * @test
 * @summary Verifies that StackFrame.getMethodType() throws UnsupportedOperationException for a frame obtained from a default StackWalker.
 * @run main StackWalkerWitupTest
 */

import java.util.Optional;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        StackWalker walker = StackWalker.getInstance();
        Optional<StackWalker.StackFrame> frame = walker.walk(s -> s.findFirst());
        if (!frame.isPresent()) {
            throw new AssertionError("expected at least one stack frame");
        }
        try {
            frame.get().getMethodType();
            throw new AssertionError("expected UnsupportedOperationException from getMethodType()");
        } catch (UnsupportedOperationException expected) {
            // expected behavior of the default StackFrame.getMethodType()
        }
    }
}
