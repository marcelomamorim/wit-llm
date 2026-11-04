/*
 * @test
 * @summary Verify that JulianCalendar.Date.setEra(null) throws NullPointerException
 * @modules java.base/sun.util.calendar
 * @run main/othervm --add-opens java.base/sun.util.calendar=ALL-UNNAMED JulianCalendarWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JulianCalendarWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> dateClass = Class.forName("sun.util.calendar.JulianCalendar$Date");
        Class<?> eraClass = Class.forName("sun.util.calendar.Era");
        Constructor<?> ctor = dateClass.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object date = ctor.newInstance();
        Method setEra = dateClass.getMethod("setEra", eraClass);
        setEra.setAccessible(true);
        try {
            setEra.invoke(date, new Object[] { null });
            throw new RuntimeException("expected NullPointerException was not thrown");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof NullPointerException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
        }
    }
}
