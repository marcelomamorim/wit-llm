/*
 * @test
 * @summary Verify that UnsafeQualifiedStaticBooleanFieldAccessorImpl.getInt always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    static boolean flag;

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
        Field field = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, false);
        Method getInt = cls.getMethod("getInt", Object.class);
        getInt.setAccessible(true);

        checkThrowsIAE(getInt, accessor, null);
        checkThrowsIAE(getInt, accessor, new Object());
        checkThrowsIAE(getInt, accessor, "text");
    }

    static void checkThrowsIAE(Method m, Object acc, Object arg) throws Exception {
        try {
            m.invoke(acc, arg);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("Unexpected cause: " + cause, cause);
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException was not thrown");
    }
}
