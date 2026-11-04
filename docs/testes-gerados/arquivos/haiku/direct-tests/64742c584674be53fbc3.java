/*
 * @test
 * @summary Verify that UnsafeStaticDoubleFieldAccessorImpl.getLong always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static double VALUE = 1.5;

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl");
        Field field = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getField("VALUE");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field);
        Method getLong = cls.getMethod("getLong", Object.class);
        getLong.setAccessible(true);

        check(getLong, accessor, null);
        check(getLong, accessor, new Object());
    }

    private static void check(Method getLong, Object accessor, Object target) throws Exception {
        try {
            getLong.invoke(accessor, target);
            throw new RuntimeException("expected InvocationTargetException");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", cause);
            }
        }
    }
}
