/*
 * @test
 * @summary Frame.pop() must throw IndexOutOfBoundsException with the documented message when the operand stack is empty
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class FrameWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void expectEmptyStackPop(Frame<Value> f, String label) {
        try {
            f.pop();
            check(false, label + ": pop() should have thrown");
        } catch (IndexOutOfBoundsException e) {
            check("Cannot pop operand off an empty stack.".equals(e.getMessage()),
                  label + ": unexpected message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Freshly created frame with no operand stack slots
        expectEmptyStackPop(new Frame<Value>(0, 0), "zero locals, zero stack");
        expectEmptyStackPop(new Frame<Value>(2, 0), "two locals, zero stack");
        expectEmptyStackPop(new Frame<Value>(5, 0), "five locals, zero stack");

        // Repeated pop on empty stack must keep failing consistently
        Frame<Value> f = new Frame<Value>(1, 0);
        expectEmptyStackPop(f, "first pop");
        expectEmptyStackPop(f, "second pop");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
