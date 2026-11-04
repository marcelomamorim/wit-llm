/*
 * @test
 * @summary Test Frame.getLocal method for valid and invalid indices
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testGetLocalValidIndex();
        testGetLocalInvalidIndex();
    }

    private static void testGetLocalValidIndex() {
        Frame<Object> frame = new Frame<>(3, 0);
        Object localValue = new Object();
        frame.values[0] = localValue;
        frame.values[1] = new Object();
        frame.values[2] = new Object();
        frame.locals = 3;

        Object result = frame.getLocal(0);
        if (result != localValue) {
            throw new AssertionError("getLocal did not return the expected value at index 0");
        }

        result = frame.getLocal(2);
        if (result != frame.values[2]) {
            throw new AssertionError("getLocal did not return the expected value at index 2");
        }
    }

    private static void testGetLocalInvalidIndex() {
        Frame<Object> frame = new Frame<>(2, 0);
        frame.values[0] = new Object();
        frame.values[1] = new Object();
        frame.locals = 2;

        try {
            frame.getLocal(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 2");
        } catch (IndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("inexistant local variable")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }

        try {
            frame.getLocal(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected, as negative index accesses array directly
        }
    }
}