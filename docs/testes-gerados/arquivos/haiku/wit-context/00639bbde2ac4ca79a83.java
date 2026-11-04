/*
 * @test
 * @summary Verify that UnsafeQualifiedStaticFloatFieldAccessorImpl.getByte(Object) throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static float sample = 1.5f;

    public static void main(String[] args) throws Exception {
        Class<?> accessorClass = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl");
        Field field = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("sample");
        Constructor<?> ctor = accessorClass.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, true);
        Method getByte = accessorClass.getMethod("getByte", Object.class);
        getByte.setAccessible(true);

        expectIllegalArgument(getByte, accessor, null);
        expectIllegalArgument(getByte, accessor, new Object());
    }

    private static void expectIllegalArgument(Method getByte, Object accessor, Object target) throws Exception {
        try {
            getByte.invoke(accessor, new Object[] { target });
            throw new RuntimeException("expected InvocationTargetException wrapping IllegalArgumentException");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e.getCause());
            }
        }
    }
}
