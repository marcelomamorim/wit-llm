/*
 * @test
 * @summary Verify that UnsafeByteFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static byte value = 42;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("value");
        Class<?> accessorClass = Class.forName("jdk.internal.reflect.UnsafeByteFieldAccessorImpl");
        Constructor<?> ctor = accessorClass.getDeclaredConstructor(Field.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field);
        Method getChar = accessorClass.getMethod("getChar", Object.class);
        getChar.setAccessible(true);

        expectIllegalArgument(getChar, accessor, new UnsafeByteFieldAccessorImplWitupTest());
        expectIllegalArgument(getChar, accessor, null);
        expectIllegalArgument(getChar, accessor, "not a holder");
    }

    private static void expectIllegalArgument(Method getChar, Object accessor, Object target) throws Exception {
        try {
            Object result = getChar.invoke(accessor, target);
            throw new AssertionError("getChar returned " + result + " instead of throwing");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected cause: " + cause, cause);
            }
        }
    }
}
