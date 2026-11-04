/*
 * @test
 * @summary Checks argument validation of Unsafe.defineAnonymousClass before native definition
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    private interface Action {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        byte[] data = new byte[0];

        expect(NullPointerException.class, () -> unsafe.defineAnonymousClass(null, data, null));
        expect(NullPointerException.class, () -> unsafe.defineAnonymousClass(String.class, null, null));
        expect(NullPointerException.class, () -> unsafe.defineAnonymousClass(null, null, null));
        expect(NullPointerException.class, () -> unsafe.defineAnonymousClass(int.class, null, null));
        expect(NullPointerException.class, () -> unsafe.defineAnonymousClass(int[].class, null, null));

        expect(IllegalArgumentException.class, () -> unsafe.defineAnonymousClass(int[].class, data, null));
        expect(IllegalArgumentException.class, () -> unsafe.defineAnonymousClass(String[].class, data, null));
        expect(IllegalArgumentException.class, () -> unsafe.defineAnonymousClass(int.class, data, null));
    }

    private static void expect(Class<? extends Throwable> type, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new AssertionError("unexpected exception: " + t, t);
        }
        throw new AssertionError("expected exception: " + type.getName());
    }
}
