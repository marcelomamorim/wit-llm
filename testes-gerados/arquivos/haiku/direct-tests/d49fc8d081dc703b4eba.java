/*
 * @test
 * @summary Field.getBoolean on a static byte field must throw IllegalArgumentException and getByte must return the stored value
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    static byte b = 5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticByteFieldAccessorImplWitupTest.class.getDeclaredField("b");

        byte v = f.getByte(null);
        if (v != 5) {
            throw new AssertionError("getByte returned " + v + " expected 5");
        }

        boolean threw = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getBoolean did not throw IllegalArgumentException");
        }
    }
}
