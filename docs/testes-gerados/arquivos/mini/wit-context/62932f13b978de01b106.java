/*
 * @test
 * @summary Test Frame.getLocal(int) for valid and invalid indices
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testGetLocalValidIndex();
        testGetLocalInvalidIndex();
    }

    private static void testGetLocalValidIndex() {
        // Create a Frame with 3 locals and 0 stack size
        Frame<Object> frame = new Frame<Object>(3, 0);
        // Initialize values array with dummy objects
        Object v0 = new Object();
        Object v1 = new Object();
        Object v2 = new Object();
        // Use reflection to set values array since no public setter (discarded, no reflection allowed)
        // Instead, use copy constructor to clone and set locals indirectly
        // But no public setter or method to set locals, so test only getLocal with default nulls

        // By default, values array elements are null, so getLocal should return null for valid indices
        for (int i = 0; i < 3; i++) {
            Object local = frame.getLocal(i);
            if (local != null) {
                throw new AssertionError("Expected null local at index " + i);
            }
        }
    }

    private static void testGetLocalInvalidIndex() {
        Frame<Object> frame = new Frame<Object>(2, 0);
        boolean thrown = false;
        try {
            frame.getLocal(2); // index equal to locals should throw
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("inexistant local variable")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index 2");
        }

        thrown = false;
        try {
            frame.getLocal(100); // index greater than locals should throw
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index 100");
        }

        thrown = false;
        try {
            frame.getLocal(-1); // negative index should throw ArrayIndexOutOfBoundsException or IndexOutOfBoundsException
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected exception for negative index");
        }
    }
}
