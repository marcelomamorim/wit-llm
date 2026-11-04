/*
 * @test
 * @summary Verify that UnsafeQualifiedIntegerFieldAccessorImpl.getByte always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class Holder {
        int value = 7;
    }

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl");
        Field f = Holder.class.getDeclaredField("value");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(f, false);
        Method m = cls.getMethod("getByte", Object.class);
        m.setAccessible(true);

        expectIllegalArgument(m, accessor, new Holder());
        expectIllegalArgument(m, accessor, null);
    }

    static void expectIllegalArgument(Method m, Object accessor, Object obj) throws Exception {
        try {
            m.invoke(accessor, obj);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause(), e);
            }
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException from getByte");
    }
}
