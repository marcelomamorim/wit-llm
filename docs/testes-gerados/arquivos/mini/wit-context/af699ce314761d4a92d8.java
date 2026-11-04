/*
 * @test
 * @summary Test Frame.push(V) for normal and exceptional behavior
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPushNormal();
        testPushThrowsIndexOutOfBoundsException();
    }

    private static void testPushNormal() {
        // Create Frame with nLocals=1 and nStack=1, so values.length = 2
        Frame<Object> frame = new Frame<Object>(1, 1);
        Object value = new Object();
        try {
            frame.push(value); // should succeed
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("push threw unexpectedly", e);
        }
    }

    private static void testPushThrowsIndexOutOfBoundsException() {
        // Create Frame with nLocals=1 and nStack=1, so values.length = 2
        Frame<Object> frame = new Frame<Object>(1, 1);
        Object value = new Object();
        // Push once to fill the stack
        frame.push(value);
        // Push second time should throw IndexOutOfBoundsException
        boolean thrown = false;
        try {
            frame.push(value);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        }
    }
}
