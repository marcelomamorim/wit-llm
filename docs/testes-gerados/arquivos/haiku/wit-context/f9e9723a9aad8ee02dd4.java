/*
 * @test
 * @summary Verifies that UnsafeQualifiedStaticDoubleFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("value");
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, Boolean.FALSE);
        Method getShort = cls.getDeclaredMethod("getShort", Object.class);
        getShort.setAccessible(true);
        checkThrowsIllegalArgument(getShort, accessor, null);
        checkThrowsIllegalArgument(getShort, accessor, new Object());
    }

    private static void checkThrowsIllegalArgument(Method m, Object target, Object arg) throws Exception {
        try {
            m.invoke(target, arg);
            throw new RuntimeException("no exception");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
        }
    }
}
