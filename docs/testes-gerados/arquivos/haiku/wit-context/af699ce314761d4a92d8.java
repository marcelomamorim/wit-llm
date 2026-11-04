/*
 * @test
 * @summary Verifies that Frame.push throws IndexOutOfBoundsException when the operand stack capacity is exhausted
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class FrameWitupTest {

    public static void main(String[] args) {
        testZeroStackCapacityThrowsOnFirstPush();
        testPushSucceedsUntilCapacityThenThrows();
        testOverflowAfterFillingStackIsRejected();
    }

    static void testZeroStackCapacityThrowsOnFirstPush() {
        Frame<Value> frame = new Frame<Value>(2, 0);
        boolean caught = false;
        try {
            frame.push(null);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("expected IndexOutOfBoundsException when nStack is 0");
        }
    }

    static void testPushSucceedsUntilCapacityThenThrows() {
        Frame<Value> frame = new Frame<Value>(1, 2);
        frame.push(null);
        frame.push(null);
        boolean caught = false;
        try {
            frame.push(null);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("expected IndexOutOfBoundsException on push beyond capacity");
        }
    }

    static void testOverflowAfterFillingStackIsRejected() {
        Frame<Value> frame = new Frame<Value>(2, 1);
        frame.push(null);
        boolean caught = false;
        try {
            frame.push(null);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("expected IndexOutOfBoundsException on second push with nStack 1");
        }
    }
}
