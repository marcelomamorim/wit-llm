/*
 * @test
 * @summary Verify that UnsafeQualifiedDoubleFieldAccessorImpl.getFloat always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl");
        Constructor<?> ctor = c.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Field f = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getField("value");
        Object accessor = ctor.newInstance(f, false);

        Method getFloat = c.getMethod("getFloat", Object.class);
        getFloat.setAccessible(true);

        checkThrowsIAE(getFloat, accessor, new UnsafeQualifiedDoubleFieldAccessorImplWitupTest());
        checkThrowsIAE(getFloat, accessor, null);
    }

    private static void checkThrowsIAE(Method getFloat, Object accessor, Object target) throws Exception {
        try {
            getFloat.invoke(accessor, target);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("Expected IllegalArgumentException but got: " + cause, cause);
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException was not thrown");
    }
}
