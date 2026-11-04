/*
 * @test
 * @summary Tests the push method of Frame class for IndexOutOfBoundsException
 * @run main FrameWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPushWithValidInput();
        testPushWithInvalidInput();
    }

    private static void testPushWithValidInput() {
        try {
            // Create a Frame with capacity for 5 locals and 5 stack
            Frame<Object> frame = new Frame<>(5, 5);
            // Push a value; should not throw
            frame.push(new Object());
            System.out.println("testPushWithValidInput passed");
        } catch (Exception e) {
            System.err.println("testPushWithValidInput failed: " + e);
        }
    }

    private static void testPushWithInvalidInput() {
        try {
            // Create a Frame with capacity for 1 local and 1 stack
            Frame<Object> frame = new Frame<>(1, 1);
            // Fill the frame to capacity
            frame.push(new Object());
            // Next push should throw IndexOutOfBoundsException
            frame.push(new Object());
            System.err.println("testPushWithInvalidInput failed: exception not thrown");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testPushWithInvalidInput passed");
        } catch (Exception e) {
            System.err.println("testPushWithInvalidInput failed: " + e);
        }
    }
}
