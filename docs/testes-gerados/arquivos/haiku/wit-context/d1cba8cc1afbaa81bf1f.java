/*
 * @test
 * @summary Field.getChar on a static int field must reject the int-to-char conversion and keep getInt/get working
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static int value = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getField("value");

        if (f.getInt(null) != 42) {
            throw new AssertionError("getInt(null) returned unexpected value");
        }
        if (!Integer.valueOf(42).equals(f.get(null))) {
            throw new AssertionError("get(null) returned unexpected value");
        }

        try {
            f.getChar(null);
            throw new AssertionError("expected IllegalArgumentException from getChar(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getChar(new Object());
            throw new AssertionError("expected IllegalArgumentException from getChar(Object)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
