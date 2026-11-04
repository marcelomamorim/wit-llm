/*
 * @test
 * @summary Verify that Field.getDouble on a static boolean field is rejected by the UnsafeStaticBooleanFieldAccessorImpl accessor and leaves the value unchanged
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (f.getBoolean(null) != true) {
            throw new AssertionError("precondition: getBoolean should return true");
        }

        try {
            double d = f.getDouble(null);
            throw new AssertionError("expected IllegalArgumentException, got " + d);
        } catch (IllegalArgumentException expected) {
        }

        try {
            double d = f.getDouble(new Object());
            throw new AssertionError("expected IllegalArgumentException for non-null obj, got " + d);
        } catch (IllegalArgumentException expected) {
        }

        if (f.getBoolean(null) != true) {
            throw new AssertionError("value changed after failed getDouble");
        }
    }
}
