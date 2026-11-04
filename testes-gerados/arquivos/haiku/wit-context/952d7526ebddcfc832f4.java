/*
 * @test
 * @summary Verifies that Frame.pop() throws IndexOutOfBoundsException when the operand stack is empty
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPopOnEmptyFrameWithNoStack();
        testPopOnEmptyFrameWithLocalsAndStack();
        testPopOnCopyOfEmptyFrame();
        testPopOnEmptyFrameRepeated();
        System.out.println("TEST PASSED");
    }

    private static void testPopOnEmptyFrameWithNoStack() {
        Frame<?> f = new Frame<>(0, 0);
        expectIndexOutOfBounds(f);
    }

    private static void testPopOnEmptyFrameWithLocalsAndStack() {
        Frame<?> f = new Frame<>(2, 3);
        expectIndexOutOfBounds(f);
    }

    private static void testPopOnCopyOfEmptyFrame() {
        Frame<?> src = new Frame<>(1, 2);
        Frame<?> copy = new Frame<>(src);
        expectIndexOutOfBounds(copy);
    }

    private static void testPopOnEmptyFrameRepeated() {
        Frame<?> f = new Frame<>(1, 1);
        expectIndexOutOfBounds(f);
        expectIndexOutOfBounds(f);
    }

    private static void expectIndexOutOfBounds(Frame<?> f) {
        boolean thrown = false;
        try {
            f.pop();
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException from pop() on empty stack");
        }
    }
}
