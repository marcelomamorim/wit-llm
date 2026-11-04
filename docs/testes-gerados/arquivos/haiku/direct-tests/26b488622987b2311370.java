/*
 * @test
 * @summary getBoolean must throw IllegalArgumentException for a static long field accessor
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    static volatile long longField = 42L;

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
        Field field = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("longField");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, true);
        Method getBoolean = cls.getMethod("getBoolean", Object.class);
        getBoolean.setAccessible(true);
        checkThrowsIllegalArgument(getBoolean, accessor, null);
        checkThrowsIllegalArgument(getBoolean, accessor, new Object());
    }

    static void checkThrowsIllegalArgument(Method m, Object acc, Object obj) throws Exception {
        try {
            m.invoke(acc, obj);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException was not thrown");
    }
}
