/*
 * @test
 * @summary Verify that UnsafeQualifiedDoubleFieldAccessorImpl.getInt always throws IllegalArgumentException
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
        Field f = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");
        Object accessor = ctor.newInstance(f, false);
        Method getInt = c.getDeclaredMethod("getInt", Object.class);
        getInt.setAccessible(true);

        checkThrowsIAE(getInt, accessor, new UnsafeQualifiedDoubleFieldAccessorImplWitupTest());
        checkThrowsIAE(getInt, accessor, null);
    }

    static void checkThrowsIAE(Method getInt, Object accessor, Object obj) throws Exception {
        try {
            getInt.invoke(accessor, obj);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException was not thrown");
    }
}
