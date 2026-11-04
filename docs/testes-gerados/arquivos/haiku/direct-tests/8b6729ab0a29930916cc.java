/*
 * @test
 * @summary Verifies that UnsafeQualifiedStaticDoubleFieldAccessorImpl.getInt always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static final double VALUE = 1.5;

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
        Field field = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, true);
        Method getInt = cls.getDeclaredMethod("getInt", Object.class);
        getInt.setAccessible(true);

        expectIllegalArgument(getInt, accessor, null);
        expectIllegalArgument(getInt, accessor, new Object());
    }

    private static void expectIllegalArgument(Method m, Object target, Object arg) throws Exception {
        try {
            m.invoke(target, arg);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException was not thrown");
    }
}
