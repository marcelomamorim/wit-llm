/*
 * @test
 * @summary Verifies that getByte on a static boolean field reached through the public java.lang.reflect.Field API throws IllegalArgumentException
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        try {
            byte b = f.getByte(null);
            throw new RuntimeException("expected IllegalArgumentException, got " + b);
        } catch (IllegalArgumentException expected) {
        }
        if (!f.getBoolean(null)) {
            throw new RuntimeException("flag value mismatch");
        }
    }
}
