/*
 * @test
 * @summary Verify that UnsafeQualifiedBooleanFieldAccessorImpl.getShort rejects calls with IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    boolean flag;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        Class<?> accessorClass = Class.forName("jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl");
        Constructor<?> ctor = accessorClass.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, false);

        Method getShort = accessorClass.getMethod("getShort", Object.class);
        getShort.setAccessible(true);

        Object target = new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        try {
            getShort.invoke(accessor, target);
            throw new AssertionError("getShort did not throw");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
