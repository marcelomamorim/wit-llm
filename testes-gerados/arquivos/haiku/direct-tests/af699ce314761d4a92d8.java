/*
 * @test
 * @summary Verifies Frame.push stack capacity behavior and overflow exception
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPushWithinCapacitySucceeds();
        testPushBeyondStackCapacityThrows();
        testZeroStackCapacityThrowsImmediately();
        testLocalsReduceStackCapacity();
        System.out.println("All Frame.push tests passed");
    }

    static void testPushWithinCapacitySucceeds() {
        Frame<Value> frame = new Frame<Value>(0, 2);
        frame.push(null);
        frame.push(null);
    }

    static void testPushBeyondStackCapacityThrows() {
        Frame<Value> frame = new Frame<Value>(0, 1);
        frame.push(null);
        try {
            frame.push(null);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            if (!"Insufficient maximum stack size.".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testZeroStackCapacityThrowsImmediately() {
        Frame<Value> frame = new Frame<Value>(1, 0);
        try {
            frame.push(null);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testLocalsReduceStackCapacity() {
        Frame<Value> frame = new Frame<Value>(2, 1);
        frame.push(null);
        try {
            frame.push(null);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
