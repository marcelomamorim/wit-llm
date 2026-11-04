/*
 * @test
 * @summary Verifies that Field.getBoolean on a non-final static Object field throws IllegalArgumentException, which is the public path that reaches UnsafeStaticObjectFieldAccessorImpl.getBoolean
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static Object value = "text";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("value");

        try {
            f.getBoolean(null);
            throw new RuntimeException("expected IllegalArgumentException for getBoolean(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getBoolean(new Object());
            throw new RuntimeException("expected IllegalArgumentException for getBoolean(Object)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
