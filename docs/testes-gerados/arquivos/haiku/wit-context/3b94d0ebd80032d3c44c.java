/*
 * @test
 * @summary Verify that Field.getLong on a static Object field throws IllegalArgumentException (UnsafeStaticObjectFieldAccessorImpl.getLong)
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static Object objectField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objectField");
        checkThrowsIllegalArgument(f, null);
        checkThrowsIllegalArgument(f, new Object());
    }

    private static void checkThrowsIllegalArgument(Field f, Object obj) throws Exception {
        try {
            long v = f.getLong(obj);
            throw new RuntimeException("expected IllegalArgumentException, got " + v);
        } catch (IllegalArgumentException expected) {
        }
    }
}
