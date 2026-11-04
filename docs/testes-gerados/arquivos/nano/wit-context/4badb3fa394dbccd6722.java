/* @test    @summary Test for Unsafe.staticFieldOffset(Field f) null argument handling    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testStaticFieldOffsetNull();
    }

    public static void testStaticFieldOffsetNull() {
        try {
            Unsafe unsafe = Unsafe.getUnsafe();
            unsafe.staticFieldOffset(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
