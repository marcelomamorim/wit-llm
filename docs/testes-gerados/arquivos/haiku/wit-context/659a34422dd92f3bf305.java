/*
 * @test
 * @summary Verifies via reflection that ImmutableGregorianDate rejects a null BaseCalendar.Date in its constructor
 * @modules java.base/sun.util.calendar
 * @run main/othervm --add-opens java.base/sun.util.calendar=ALL-UNNAMED ImmutableGregorianDateWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ImmutableGregorianDateWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> immutable = Class.forName("sun.util.calendar.ImmutableGregorianDate");
        Class<?> dateType = Class.forName("sun.util.calendar.BaseCalendar$Date");
        Constructor<?> ctor = immutable.getDeclaredConstructor(dateType);
        ctor.setAccessible(true);
        try {
            ctor.newInstance((Object) null);
            throw new AssertionError("expected NullPointerException for null date");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
