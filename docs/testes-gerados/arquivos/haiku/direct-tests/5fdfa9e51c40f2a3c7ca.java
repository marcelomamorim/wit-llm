/*
 * @test
 * @summary Tests null-argument handling and basic offset behavior of Unsafe.objectFieldOffset
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static class Holder {
        int a;
        long b;
    }

    interface Action {
        void run();
    }

    static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        Unsafe u = Unsafe.getUnsafe();
        if (u == null) {
            throw new RuntimeException("getUnsafe returned null");
        }

        expectNpe(() -> u.objectFieldOffset(null, "a"));
        expectNpe(() -> u.objectFieldOffset(Holder.class, null));
        expectNpe(() -> u.objectFieldOffset(null, null));

        long oa = u.objectFieldOffset(Holder.class, "a");
        long ob = u.objectFieldOffset(Holder.class, "b");

        if (oa < 0 || ob < 0) {
            throw new RuntimeException("Negative offset: " + oa + ", " + ob);
        }
        if (oa == ob) {
            throw new RuntimeException("Distinct fields returned same offset: " + oa);
        }
        if (oa != u.objectFieldOffset(Holder.class, "a")) {
            throw new RuntimeException("Offset not stable across calls for field a");
        }
        if (ob != u.objectFieldOffset(Holder.class, "b")) {
            throw new RuntimeException("Offset not stable across calls for field b");
        }
    }
}
