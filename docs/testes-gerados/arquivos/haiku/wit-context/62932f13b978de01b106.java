/*
 * @test
 * @summary Tests bounds checking of Frame.getLocal for local variable indexes
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class FrameWitupTest {
    public static void main(String[] args) {
        Frame<Value> f = new Frame<Value>(2, 1);
        if (f.getLocal(0) != null || f.getLocal(1) != null) {
            throw new RuntimeException("unexpected non-null initial local value");
        }
        expectIndexOutOfBounds(f, 2);
        expectIndexOutOfBounds(f, 3);
        expectIndexOutOfBounds(f, -1);

        Frame<Value> empty = new Frame<Value>(0, 0);
        expectIndexOutOfBounds(empty, 0);
    }

    static void expectIndexOutOfBounds(Frame<Value> f, int i) {
        try {
            f.getLocal(i);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + i);
    }
}
