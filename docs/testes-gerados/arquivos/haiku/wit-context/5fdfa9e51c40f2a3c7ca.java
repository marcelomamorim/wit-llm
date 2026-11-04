/*
 * @test
 * @summary Verify Unsafe.objectFieldOffset null-argument checks and deterministic offsets for instance fields
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static class Holder {
        int a;
        int b;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        checkThrowsNpe(() -> unsafe.objectFieldOffset(null, "a"));
        checkThrowsNpe(() -> unsafe.objectFieldOffset(Holder.class, null));
        checkThrowsNpe(() -> unsafe.objectFieldOffset(null, null));

        long offA = unsafe.objectFieldOffset(Holder.class, "a");
        long offB = unsafe.objectFieldOffset(Holder.class, "b");
        if (offA == offB) {
            throw new RuntimeException("distinct fields returned same offset");
        }
        if (offA != unsafe.objectFieldOffset(Holder.class, "a")) {
            throw new RuntimeException("offset not stable across calls");
        }
    }

    static void checkThrowsNpe(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }
}
