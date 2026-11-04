/*
 * @test
 * @summary Verify that UnsafeQualifiedStaticLongFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static long value;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");
        Class<?> accessorClass = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
        Constructor<?> ctor = accessorClass.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(field, Boolean.FALSE);

        Method getChar = accessorClass.getMethod("getChar", Object.class);

        checkThrowsIAE(getChar, accessor, null);
        checkThrowsIAE(getChar, accessor, new Object());
        checkThrowsIAE(getChar, accessor, "notAStaticTarget");
    }

    private static void checkThrowsIAE(Method getChar, Object accessor, Object target) throws Exception {
        try {
            getChar.invoke(accessor, target);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("Expected IllegalArgumentException but got: " + cause, e);
            }
            return;
        }
        throw new RuntimeException("getChar did not throw for target: " + target);
    }
}
