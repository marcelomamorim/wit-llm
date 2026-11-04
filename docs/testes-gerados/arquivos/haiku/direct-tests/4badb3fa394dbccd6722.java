/*
 * @test
 * @summary Tests Unsafe.staticFieldOffset null handling and deterministic offsets for static fields
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static int intField;
    static long longField;

    public static void main(String[] args) throws Exception {
        testNullFieldThrowsNPE();
        testRepeatedCallsReturnSameOffset();
        testOffsetIsNonNegative();
    }

    static void testNullFieldThrowsNPE() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean thrown = false;
        try {
            unsafe.staticFieldOffset(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null Field");
        }
    }

    static void testRepeatedCallsReturnSameOffset() throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Field f = UnsafeWitupTest.class.getDeclaredField("intField");
        long first = unsafe.staticFieldOffset(f);
        long second = unsafe.staticFieldOffset(f);
        if (first != second) {
            throw new RuntimeException("Offset not stable: " + first + " vs " + second);
        }
    }

    static void testOffsetIsNonNegative() throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Field f = UnsafeWitupTest.class.getDeclaredField("longField");
        long offset = unsafe.staticFieldOffset(f);
        if (offset < 0) {
            throw new RuntimeException("Unexpected negative offset: " + offset);
        }
    }
}
