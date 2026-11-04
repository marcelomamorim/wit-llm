/*
 * @test
 * @summary Verifies that UnsafeQualifiedDoubleFieldAccessorImpl.getFloat always rejects access with IllegalArgumentException
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static class Target {
        public double d = 1.5;
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl");
        Constructor<?> ctor = c.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Field f = Target.class.getField("d");
        Object acc = ctor.newInstance(f, false);
        Method m = c.getDeclaredMethod("getFloat", Object.class);
        m.setAccessible(true);

        checkThrowsIAE(m, acc, new Target());
        checkThrowsIAE(m, acc, null);
    }

    static void checkThrowsIAE(Method m, Object acc, Object obj) throws Exception {
        try {
            m.invoke(acc, obj);
            throw new RuntimeException("getFloat did not throw");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
        }
    }
}
