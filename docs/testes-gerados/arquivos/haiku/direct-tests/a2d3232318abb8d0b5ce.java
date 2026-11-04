/*
 * @test
 * @summary Verify Unsafe.arrayBaseOffset null handling and stable non-negative results for array classes
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        testNullThrowsNullPointerException(unsafe);
        testArrayClassesReturnStableNonNegativeOffsets(unsafe);
        System.out.println("PASS");
    }

    static void testNullThrowsNullPointerException(Unsafe unsafe) {
        try {
            unsafe.arrayBaseOffset(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null array class");
    }

    static void testArrayClassesReturnStableNonNegativeOffsets(Unsafe unsafe) {
        Class<?>[] classes = { int[].class, byte[].class, long[].class, Object[].class, String[].class };
        for (Class<?> c : classes) {
            int first = unsafe.arrayBaseOffset(c);
            if (first < 0) {
                throw new RuntimeException("Negative base offset for " + c.getName() + ": " + first);
            }
            int second = unsafe.arrayBaseOffset(c);
            if (first != second) {
                throw new RuntimeException("Unstable base offset for " + c.getName() + ": " + first + " vs " + second);
            }
        }
    }
}
