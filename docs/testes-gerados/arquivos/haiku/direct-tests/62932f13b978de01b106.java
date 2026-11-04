/*
 * @test
 * @summary Tests Frame.getLocal returns null for unset locals and throws IndexOutOfBoundsException for out-of-range indexes
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIndexOutOfBounds(Frame f, int index, String message) {
        try {
            f.getLocal(index);
        } catch (IndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testUnsetLocalsAreNull() {
        Frame f = new Frame(3, 2);
        for (int i = 0; i < 3; i++) {
            check(f.getLocal(i) == null, "unset local " + i + " should be null");
        }
    }

    static void testIndexEqualToLocalsThrows() {
        Frame f = new Frame(3, 2);
        expectIndexOutOfBounds(f, 3, "getLocal(nLocals) should throw");
    }

    static void testIndexFarBeyondLocalsThrows() {
        Frame f = new Frame(2, 0);
        expectIndexOutOfBounds(f, 100, "getLocal(100) should throw");
    }

    static void testZeroLocalsThrowsOnIndexZero() {
        Frame f = new Frame(0, 1);
        expectIndexOutOfBounds(f, 0, "getLocal(0) on frame with no locals should throw");
    }

    static void testNegativeIndexThrows() {
        Frame f = new Frame(2, 0);
        expectIndexOutOfBounds(f, -1, "getLocal(-1) should throw IndexOutOfBoundsException");
    }

    static void testCopiedFrameKeepsLocalBound() {
        Frame src = new Frame(2, 1);
        Frame copy = new Frame(src);
        check(copy.getLocal(0) == null, "copied unset local 0 should be null");
        check(copy.getLocal(1) == null, "copied unset local 1 should be null");
        expectIndexOutOfBounds(copy, 2, "copied frame getLocal(2) should throw");
    }

    public static void main(String[] args) {
        testUnsetLocalsAreNull();
        testIndexEqualToLocalsThrows();
        testIndexFarBeyondLocalsThrows();
        testZeroLocalsThrowsOnIndexZero();
        testNegativeIndexThrows();
        testCopiedFrameKeepsLocalBound();
    }
}
