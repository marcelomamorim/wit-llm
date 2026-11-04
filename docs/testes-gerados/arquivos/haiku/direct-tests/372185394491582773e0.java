/*
 * @test
 * @summary Verifies Frame.merge change detection and access-based skipping of locals
 * @modules java.base/jdk.internal.org.objectweb.asm.tree.analysis
 * @run main FrameWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class FrameWitupTest {

    static final class TestValue implements Value {
        final int id;

        TestValue(int id) {
            this.id = id;
        }

        public int getSize() {
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof TestValue)) {
                return false;
            }
            return ((TestValue) o).id == id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        // Case 1: identical locals, nothing accessed -> no changes
        Frame<TestValue> f1 = new Frame<>(2, 0);
        f1.setLocal(0, new TestValue(1));
        f1.setLocal(1, new TestValue(2));
        Frame<TestValue> f2 = new Frame<>(2, 0);
        f2.setLocal(0, new TestValue(1));
        f2.setLocal(1, new TestValue(2));
        boolean[] none = new boolean[2];
        check(!f1.merge(f2, none), "identical frames must report no changes");
        check(f1.getLocal(0).equals(new TestValue(1)), "local 0 must be unchanged");
        check(f1.getLocal(1).equals(new TestValue(2)), "local 1 must be unchanged");

        // Case 2: differing local, not accessed -> change reported and value copied
        f2.setLocal(0, new TestValue(9));
        check(f1.merge(f2, none), "differing non-accessed local must report change");
        check(f1.getLocal(0).equals(new TestValue(9)), "local 0 must be overwritten by source");
        check(f1.getLocal(1).equals(new TestValue(2)), "local 1 must remain unchanged");

        // Case 3: differing locals, but accessed ones are skipped
        Frame<TestValue> g1 = new Frame<>(2, 0);
        g1.setLocal(0, new TestValue(1));
        g1.setLocal(1, new TestValue(2));
        Frame<TestValue> g2 = new Frame<>(2, 0);
        g2.setLocal(0, new TestValue(9));
        g2.setLocal(1, new TestValue(7));
        boolean[] access = new boolean[] {true, false};
        check(g1.merge(g2, access), "non-accessed local 1 differs, change expected");
        check(g1.getLocal(0).equals(new TestValue(1)), "accessed local 0 must not be merged");
        check(g1.getLocal(1).equals(new TestValue(7)), "non-accessed local 1 must be merged");

        // Case 4: all locals accessed -> no changes even though values differ
        Frame<TestValue> h1 = new Frame<>(2, 0);
        h1.setLocal(0, new TestValue(1));
        h1.setLocal(1, new TestValue(2));
        Frame<TestValue> h2 = new Frame<>(2, 0);
        h2.setLocal(0, new TestValue(9));
        h2.setLocal(1, new TestValue(7));
        boolean[] all = new boolean[] {true, true};
        check(!h1.merge(h2, all), "all-accessed merge must report no changes");
        check(h1.getLocal(0).equals(new TestValue(1)), "accessed local 0 must stay");
        check(h1.getLocal(1).equals(new TestValue(2)), "accessed local 1 must stay");
    }
}
