/*
 * @test
 * @summary Verifies that UnsafeQualifiedDoubleFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static class Holder {
        public double value = 1.5;
    }

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl");
        Field field = Holder.class.getField("value");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, Boolean.FALSE);
        Method getBoolean = cls.getMethod("getBoolean", Object.class);
        getBoolean.setAccessible(true);

        checkThrowsIllegalArgument(getBoolean, accessor, new Holder());
        checkThrowsIllegalArgument(getBoolean, accessor, null);
    }

    private static void checkThrowsIllegalArgument(Method m, Object accessor, Object obj) {
        try {
            m.invoke(accessor, obj);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause(), e);
            }
            return;
        } catch (IllegalAccessException e) {
            throw new AssertionError("Unexpected access failure", e);
        }
        throw new AssertionError("Expected IllegalArgumentException was not thrown");
    }
}
