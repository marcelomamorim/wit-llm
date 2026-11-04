/*
 * @test
 * @summary Verify that UnsafeQualifiedIntegerFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int value = 7;
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl");
        Field f = Holder.class.getField("value");
        Constructor<?> ctor = c.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(f, false);
        Method getChar = c.getDeclaredMethod("getChar", Object.class);
        getChar.setAccessible(true);

        expectIllegalArgument(getChar, accessor, new Holder());
        expectIllegalArgument(getChar, accessor, null);
    }

    static void expectIllegalArgument(Method m, Object target, Object obj) throws Exception {
        try {
            m.invoke(target, obj);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new AssertionError("unexpected cause: " + e.getCause(), e);
            }
            return;
        }
        throw new AssertionError("getChar did not throw IllegalArgumentException");
    }
}
