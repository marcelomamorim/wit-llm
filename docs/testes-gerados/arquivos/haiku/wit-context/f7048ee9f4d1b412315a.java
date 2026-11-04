/*
 * @test
 * @summary Verify that reading a static boolean field as int through the public reflection API throws IllegalArgumentException, and that the boolean read path still works
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean on static boolean field returned unexpected value");
        }

        try {
            f.getInt(null);
            throw new RuntimeException("expected IllegalArgumentException from getInt(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getInt(new Object());
            throw new RuntimeException("expected IllegalArgumentException from getInt(Object)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
