/*
 * @test
 * @summary Witup-generated tests for Unsafe.objectFieldOffset(Field) null check and offset behavior
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static class Holder {
        int a;
        long b;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        testNullFieldThrowsNPE(unsafe);
        testOffsetIsStable(unsafe);
        testDistinctFieldsHaveDistinctOffsets(unsafe);
    }

    static void testNullFieldThrowsNPE(Unsafe unsafe) {
        boolean thrown = false;
        try {
            unsafe.objectFieldOffset((Field) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null Field");
        }
    }

    static void testOffsetIsStable(Unsafe unsafe) throws NoSuchFieldException {
        Field f = Holder.class.getDeclaredField("a");
        long first = unsafe.objectFieldOffset(f);
        long second = unsafe.objectFieldOffset(f);
        if (first != second) {
            throw new AssertionError("offset changed between calls: " + first + " vs " + second);
        }
    }

    static void testDistinctFieldsHaveDistinctOffsets(Unsafe unsafe) throws NoSuchFieldException {
        long offA = unsafe.objectFieldOffset(Holder.class.getDeclaredField("a"));
        long offB = unsafe.objectFieldOffset(Holder.class.getDeclaredField("b"));
        if (offA == offB) {
            throw new AssertionError("distinct fields share offset: " + offA);
        }
    }
}
