/*
 * @test
 * @summary Verifies the declared contract of UnsafeQualifiedCharacterFieldAccessorImpl.getBoolean (returns boolean, declares IllegalArgumentException)
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Method;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl");
        Method m = c.getDeclaredMethod("getBoolean", Object.class);

        if (m.getReturnType() != boolean.class) {
            throw new RuntimeException("getBoolean must return boolean, got " + m.getReturnType());
        }

        Class<?>[] exceptions = m.getExceptionTypes();
        if (exceptions.length != 1 || exceptions[0] != IllegalArgumentException.class) {
            throw new RuntimeException("getBoolean must declare IllegalArgumentException only");
        }
    }
}
