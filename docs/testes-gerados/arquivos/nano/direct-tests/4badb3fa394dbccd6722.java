/* @test    @summary Test for Unsafe.staticFieldOffset(Field f) method    @run main UnsafeWitupTest */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testNullFieldThrowsException();
        testValidFieldReturnsOffset();
    }

    private static void testNullFieldThrowsException() {
        try {
            Unsafe unsafe = Unsafe.getUnsafe();
            unsafe.staticFieldOffset(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }

    private static void testValidFieldReturnsOffset() {
        try {
            Class<?> cls = Unsafe.class;
            Field field = cls.getDeclaredField("theUnsafe");
            Unsafe unsafe = Unsafe.getUnsafe();
            long offset = unsafe.staticFieldOffset(field);
            if (offset < 0) {
                throw new AssertionError("Offset should be non-negative, but was: " + offset);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Field 'theUnsafe' not found", e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
