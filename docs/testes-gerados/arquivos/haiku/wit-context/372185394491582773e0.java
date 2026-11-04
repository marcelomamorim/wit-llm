/*
 * @test
 * @summary Witup generated tests for Frame.merge(Frame, boolean[]) in the ASM tree analysis package
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class FrameWitupTest {

    static final class TestValue implements Value {
        public int getSize() {
            return 1;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestValue a = new TestValue();
        TestValue b = new TestValue();
        TestValue c = new TestValue();

        Frame<TestValue> same1 = new Frame<>(2, 0);
        Frame<TestValue> same2 = new Frame<>(2, 0);
        same1.setLocal(0, a);
        same1.setLocal(1, a);
        same2.setLocal(0, a);
        same2.setLocal(1, a);
        boolean changed = same1.merge(same2, new boolean[] {false, false});
        check(!changed, "equal locals must not report changes");
        check(same1.getLocal(0) == a && same1.getLocal(1) == a, "equal locals must be preserved");

        Frame<TestValue> f1 = new Frame<>(2, 0);
        Frame<TestValue> f2 = new Frame<>(2, 0);
        f1.setLocal(0, a);
        f1.setLocal(1, a);
        f2.setLocal(0, b);
        f2.setLocal(1, c);
        changed = f1.merge(f2, new boolean[] {false, true});
        check(changed, "non-accessed differing local must report change");
        check(f1.getLocal(0) == b, "non-accessed differing local must take value from frame");
        check(f1.getLocal(1) == a, "accessed local must not be overwritten");

        Frame<TestValue> g1 = new Frame<>(1, 0);
        Frame<TestValue> g2 = new Frame<>(1, 0);
        g1.setLocal(0, a);
        g2.setLocal(0, b);
        changed = g1.merge(g2, new boolean[] {true});
        check(!changed, "all accessed locals must not report changes");
        check(g1.getLocal(0) == a, "accessed local must remain unchanged");
    }
}
