/*
 * @test
 * @summary Checks observable behavior of JulianCalendar.Date.setEra for null and for the era values in the static eras array
 * @modules java.base/sun.util.calendar
 * @run main/othervm --add-opens java.base/sun.util.calendar=ALL-UNNAMED JulianCalendarWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JulianCalendarWitupTest {

    static void expectThrows(Method m, Object target, Object arg, Class<?> expected) throws Exception {
        try {
            m.invoke(target, arg);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (expected.isInstance(cause)) {
                return;
            }
            throw new AssertionError("expected " + expected.getName() + " but got " + cause, cause);
        }
        throw new AssertionError("expected " + expected.getName() + " but no exception was thrown");
    }

    public static void main(String[] args) throws Exception {
        Class<?> calCls = Class.forName("sun.util.calendar.JulianCalendar");
        Constructor<?> ctor = calCls.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object cal = ctor.newInstance();

        Method getCalendarDate = calCls.getMethod("getCalendarDate");
        Object date = getCalendarDate.invoke(cal);

        Field erasField = calCls.getDeclaredField("eras");
        erasField.setAccessible(true);
        Object[] eras = (Object[]) erasField.get(null);

        Method setEra = date.getClass().getMethod("setEra", eras[0].getClass());
        setEra.setAccessible(true);

        expectThrows(setEra, date, null, NullPointerException.class);

        expectThrows(setEra, date, eras[1], IllegalArgumentException.class);
    }
}
