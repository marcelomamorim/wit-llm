/*
 * @test
 * @summary Test getLocal method of Frame class for valid and invalid indices
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testGetLocalValidIndex();
        testGetLocalInvalidIndex();
    }

    private static void testGetLocalValidIndex() {
        try {
            Object[] values = new Object[3];
            values[0] = "a";
            values[1] = "b";
            values[2] = "c";
            Frame<Object> frame = new Frame<>(3, 2);
            for (int i = 0; i < values.length; i++) {
                frame.values[i] = values[i];
            }
            Object result = frame.getLocal(1);
            if (!"b".equals(result)) {
                throw new AssertionError("Expected 'b', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid index test", e);
        }
    }

    private static void testGetLocalInvalidIndex() {
        try {
            Frame<Object> frame = new Frame<>(2, 2);
            frame.values[0] = "x";
            frame.values[1] = "y";
            frame.getLocal(3);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in invalid index test", e);
        }
    }
}
