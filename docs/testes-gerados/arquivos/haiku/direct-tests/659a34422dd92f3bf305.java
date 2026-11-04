/*
 * @test
 * @summary Verify the public signature and return type of ImmutableGregorianDate.addMonth via reflection
 * @modules java.base/sun.util.calendar
 * @run main ImmutableGregorianDateWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ImmutableGregorianDateWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.util.calendar.ImmutableGregorianDate");
        Method m = c.getDeclaredMethod("addMonth", int.class);

        if (!Modifier.isPublic(m.getModifiers())) {
            throw new AssertionError("addMonth(int) must be public");
        }
        if (Modifier.isStatic(m.getModifiers())) {
            throw new AssertionError("addMonth(int) must not be static");
        }
        if (!"sun.util.calendar.CalendarDate".equals(m.getReturnType().getName())) {
            throw new AssertionError("Unexpected return type: " + m.getReturnType().getName());
        }
        System.out.println("PASS");
    }
}
