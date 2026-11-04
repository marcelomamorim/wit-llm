/*
 * @test
 * @summary Test Frame.push(V) method for normal and exceptional behavior
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPushNormal();
        testPushOverflow();
    }

    private static void testPushNormal() {
        // Create a Frame with 1 local and 1 stack slot
        Frame<Object> frame = new Frame<>(1, 1);
        Object value = new Object();
        frame.push(value);
        // If no exception, test passed
    }

    private static void testPushOverflow() {
        Frame<Object> frame = new Frame<>(1, 1);
        Object value = new Object();
        frame.push(value); // fills the only stack slot
        try {
            frame.push(value); // should throw IndexOutOfBoundsException
            throw new Error("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            if (!"Insufficient maximum stack size.".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
