/* @test    @summary Test Unsafe.arrayBaseOffset method for various class inputs    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testArrayBaseOffsetWithValidClass();
        testArrayBaseOffsetWithNull();
        testArrayBaseOffsetWithPrimitiveClass();
    }

    private static void testArrayBaseOffsetWithValidClass() {
        Unsafe unsafe = Unsafe.getUnsafe();
        int offset = unsafe.arrayBaseOffset(byte[].class);
        System.out.println("arrayBaseOffset(byte[].class) = " + offset);
        assert offset >= 0 : "Offset should be non-negative";
    }

    private static void testArrayBaseOffsetWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean exceptionThrown = false;
        try {
            unsafe.arrayBaseOffset(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown for null class";
    }

    private static void testArrayBaseOffsetWithPrimitiveClass() {
        Unsafe unsafe = Unsafe.getUnsafe();
        int offset = unsafe.arrayBaseOffset(int.class);
        System.out.println("arrayBaseOffset(int.class) = " + offset);
        assert offset >= 0 : "Offset for primitive class should be non-negative";
    }
}
