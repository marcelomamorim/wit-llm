/*
 * @test
 * @summary Witup-derived tests for Unsafe.arrayIndexScale null guard and basic array scale behavior
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean thrown = false;
        try {
            unsafe.arrayIndexScale(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null arrayClass");
        }

        int intScale = unsafe.arrayIndexScale(int[].class);
        if (intScale <= 0) {
            throw new RuntimeException("int[] scale must be positive: " + intScale);
        }

        int byteScale = unsafe.arrayIndexScale(byte[].class);
        if (byteScale <= 0) {
            throw new RuntimeException("byte[] scale must be positive: " + byteScale);
        }

        int objScale = unsafe.arrayIndexScale(Object[].class);
        if (objScale <= 0) {
            throw new RuntimeException("Object[] scale must be positive: " + objScale);
        }
    }
}
