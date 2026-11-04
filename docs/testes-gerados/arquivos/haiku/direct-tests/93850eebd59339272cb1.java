/*
 * @test
 * @summary getLong on an object-field accessor must throw IllegalArgumentException
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public Object f;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl");
        Field field = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("f");
        Constructor<?> ctor = c.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, false);

        Method getLong = c.getDeclaredMethod("getLong", Object.class);
        getLong.setAccessible(true);

        checkThrowsIAE(getLong, accessor, new Object());
        checkThrowsIAE(getLong, accessor, (Object) null);
    }

    static void checkThrowsIAE(Method m, Object acc, Object arg) throws Exception {
        try {
            m.invoke(acc, arg);
            throw new RuntimeException("expected InvocationTargetException");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + cause, e);
            }
        }
    }
}
