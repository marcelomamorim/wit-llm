/* @test    @summary Test pop() method of Frame class for normal and exceptional behavior    @run main FrameWitupTest */

import java.util.Arrays;
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPopNormal();
        testPopException();
    }

    static void testPopNormal() {
        Frame<Object> frame = new Frame<>(2, 2);
        // Initialize values array with dummy objects
        Object val1 = new Object();
        Object val2 = new Object();
        frame.values = new Object[] { val1, val2 };
        frame.locals = 0;
        frame.top = 2;
        try {
            Object result1 = frame.pop();
            assert result1 == val2 : "Expected val2";
            Object result2 = frame.pop();
            assert result2 == val1 : "Expected val1";
            System.out.println("testPopNormal passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testPopNormal", e);
        }
    }

    static void testPopException() {
        Frame<Object> frame = new Frame<>(1, 1);
        frame.values = new Object[1];
        frame.locals = 0;
        frame.top = 0; // empty stack
        boolean exceptionThrown = false;
        try {
            frame.pop();
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Cannot pop operand off an empty stack") : "Unexpected message";
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException";
        System.out.println("testPopException passed.");
    }
}
