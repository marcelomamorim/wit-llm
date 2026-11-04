/*
 * @test
 * @summary Verify UnsafeQualifiedDoubleFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public double d = 1.5;

    static final String CLS = "jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl";

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName(CLS);
        Constructor<?> ctor = c.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Field f = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getDeclaredField("d");

        Object readWrite = ctor.newInstance(f, false);
        Object readOnly = ctor.newInstance(f, true);

        Method getBoolean = c.getMethod("getBoolean", Object.class);
        getBoolean.setAccessible(true);

        expectIAE(getBoolean, readWrite, new UnsafeQualifiedDoubleFieldAccessorImplWitupTest());
        expectIAE(getBoolean, readWrite, null);
        expectIAE(getBoolean, readOnly, new UnsafeQualifiedDoubleFieldAccessorImplWitupTest());
        expectIAE(getBoolean, readOnly, null);
    }

    static void expectIAE(Method m, Object target, Object arg) throws Exception {
        try {
            m.invoke(target, arg);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("Unexpected cause: " + cause, cause);
            }
        }
    }
}
