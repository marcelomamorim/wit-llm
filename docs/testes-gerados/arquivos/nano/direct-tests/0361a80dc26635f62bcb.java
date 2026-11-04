/* @test    @summary Test ensureClassInitialized method of Unsafe class    @run main UnsafeEnsureClassInitializedTest */

import jdk.internal.misc.Unsafe;

public class UnsafeEnsureClassInitializedTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.ensureClassInitialized(String.class);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
        System.out.println("ensureClassInitialized(String.class) executed successfully.");
    }
}
