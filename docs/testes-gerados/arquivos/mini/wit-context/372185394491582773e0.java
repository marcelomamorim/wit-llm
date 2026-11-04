/*
 * @test
 * @summary Test deterministic behavior of Frame.merge method
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testMergeNoChange();
        testMergeWithChange();
    }

    private static void testMergeNoChange() {
        Frame<Object> f1 = new Frame<>(2, 0);
        Frame<Object> f2 = new Frame<>(2, 0);
        boolean[] access = new boolean[] {false, false};

        // Initialize values to same objects to avoid changes
        f1.values[0] = "A";
        f1.values[1] = "B";
        f2.values[0] = "A";
        f2.values[1] = "B";

        boolean changed = f1.merge(f2, access);
        if (changed) {
            throw new AssertionError("merge should return false when no values differ");
        }
    }

    private static void testMergeWithChange() {
        Frame<Object> f1 = new Frame<>(2, 0);
        Frame<Object> f2 = new Frame<>(2, 0);
        boolean[] access = new boolean[] {false, false};

        // Initialize values so that f2 differs from f1 at index 1
        f1.values[0] = "A";
        f1.values[1] = "B";
        f2.values[0] = "A";
        f2.values[1] = "C";

        boolean changed = f1.merge(f2, access);
        if (!changed) {
            throw new AssertionError("merge should return true when values differ");
        }
        if (!f1.values[1].equals("C")) {
            throw new AssertionError("merge should update values array at differing index");
        }
    }
}
