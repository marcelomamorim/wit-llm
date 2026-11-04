/*
 * @test
 * @summary Verifies that UnsafeQualifiedShortFieldAccessorImpl.getChar always rejects access with IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public short s = 7;

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl");
        Field f = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("s");
        Constructor<?> ctor = cls.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(f, Boolean.FALSE);
        Method getChar = cls.getMethod("getChar", Object.class);
        getChar.setAccessible(true);

        checkThrowsIAE(accessor, getChar, new UnsafeQualifiedShortFieldAccessorImplWitupTest());
        checkThrowsIAE(accessor, getChar, null);
    }

    static void checkThrowsIAE(Object accessor, Method getChar, Object target) throws Exception {
        try {
            getChar.invoke(accessor, target);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException was not thrown");
    }
}
