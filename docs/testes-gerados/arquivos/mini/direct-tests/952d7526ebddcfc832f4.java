/*
 * @test
 * @summary Test Frame.pop() method behavior including empty stack exception
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPopOnNonEmptyStack();
        testPopOnEmptyStackThrows();
    }

    private static void testPopOnNonEmptyStack() {
        // Create a Frame with 1 local and 1 stack slot
        Frame<Object> frame = new Frame<>(1, 1);
        // Manually set top to 1 to simulate one value on stack
        // and set a value at values[locals + top - 1] = values[1]
        frame.top = 1;
        Object value = new Object();
        frame.values[1] = value;

        Object popped = frame.pop();
        if (popped != value) {
            throw new AssertionError("pop() did not return the expected value");
        }
        if (frame.top != 0) {
            throw new AssertionError("top was not decremented after pop()");
        }
    }

    private static void testPopOnEmptyStackThrows() {
        Frame<Object> frame = new Frame<>(1, 1);
        frame.top = 0; // empty stack
        try {
            frame.pop();
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Cannot pop operand off an empty stack.")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
