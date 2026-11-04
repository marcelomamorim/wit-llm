/*
 * @test
 * @summary Verify that UnsafeQualifiedShortFieldAccessorImpl.getLong widens the short field value to long
 * @modules java.base/jdk.internal.reflect
 * @run main/othervm --add-opens java.base/jdk.internal.reflect=ALL-UNNAMED UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Holder {
        short s;
    }

    static Object newAccessor(Field f, boolean readOnly) throws Exception {
        Class<?> c = Class.forName("jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl");
        Constructor<?> ctor = c.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        try {
            return ctor.newInstance(f, readOnly);
        } catch (InvocationTargetException e) {
            throw new AssertionError("constructor failed", e.getCause());
        }
    }

    static long invokeGetLong(Object accessor, Object obj) throws Exception {
        Method m = accessor.getClass().getDeclaredMethod("getLong", Object.class);
        m.setAccessible(true);
        try {
            return (Long) m.invoke(accessor, obj);
        } catch (InvocationTargetException e) {
            throw new AssertionError("getLong failed", e.getCause());
        }
    }

    static void check(long expected, long actual, String what) {
        if (expected != actual) {
            throw new AssertionError(what +": expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("s");
        Object accessor = newAccessor(f, false);
        Holder h = new Holder();

        h.s = 0;
        check(0L, invokeGetLong(accessor, h), "zero");

        h.s = 42;
        check(42L, invokeGetLong(accessor, h), "positive");

        h.s = -5;
        check(-5L, invokeGetLong(accessor, h), "negative sign extension");

        h.s = Short.MAX_VALUE;
        check((long) Short.MAX_VALUE, invokeGetLong(accessor, h), "max short");

        h.s = Short.MIN_VALUE;
        check((long) Short.MIN_VALUE, invokeGetLong(accessor, h), "min short");

        Object readOnlyAccessor = newAccessor(f, true);
        h.s = 7;
        check(7L, invokeGetLong(readOnlyAccessor, h), "read-only accessor read");
    }
}
