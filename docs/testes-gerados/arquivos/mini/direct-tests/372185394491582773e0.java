/*
 * @test
 * @summary Test Frame.merge method behavior
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testMergeNoChange();
        testMergeWithChange();
        testMergeWithAccessTrue();
    }

    private static void testMergeNoChange() {
        Frame<String> f1 = new Frame<>(3, 0);
        Frame<String> f2 = new Frame<>(3, 0);
        String v0 = "A";
        String v1 = "B";
        String v2 = "C";
        f1.values[0] = v0;
        f1.values[1] = v1;
        f1.values[2] = v2;
        f2.values[0] = v0;
        f2.values[1] = v1;
        f2.values[2] = v2;
        boolean[] access = new boolean[] {false, false, false};
        boolean changed = f1.merge(f2, access);
        if (changed) {
            throw new AssertionError("merge should return false when no values differ and access is false");
        }
    }

    private static void testMergeWithChange() {
        Frame<String> f1 = new Frame<>(2, 0);
        Frame<String> f2 = new Frame<>(2, 0);
        String v0 = "X";
        String v1 = "Y";
        String v2 = "Z";
        f1.values[0] = v0;
        f1.values[1] = v1;
        f2.values[0] = v2; // different
        f2.values[1] = v1;
        boolean[] access = new boolean[] {false, false};
        boolean changed = f1.merge(f2, access);
        if (!changed) {
            throw new AssertionError("merge should return true when a value differs and access is false");
        }
        if (!f1.values[0].equals(v2)) {
            throw new AssertionError("merge should update values when access is false and values differ");
        }
    }

    private static void testMergeWithAccessTrue() {
        Frame<String> f1 = new Frame<>(2, 0);
        Frame<String> f2 = new Frame<>(2, 0);
        String v0 = "M";
        String v1 = "N";
        String v2 = "O";
        f1.values[0] = v0;
        f1.values[1] = v1;
        f2.values[0] = v2; // different
        f2.values[1] = v1;
        boolean[] access = new boolean[] {true, false};
        boolean changed = f1.merge(f2, access);
        if (changed) {
            throw new AssertionError("merge should not update values where access is true");
        }
        if (!f1.values[0].equals(v0)) {
            throw new AssertionError("value at index 0 should not be updated because access[0] is true");
        }
        if (f1.values[1] != v1) {
            throw new AssertionError("value at index 1 should remain unchanged");
        }
    }
}