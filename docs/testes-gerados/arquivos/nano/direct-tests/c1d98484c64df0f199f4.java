/*
 * @test
 * @summary Test Calendar.setInstant(Date) method for correct behavior.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Date;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetInstantWithValidDate();
        testSetInstantWithNull();
    }

    static void testSetInstantWithValidDate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date(1638316800000L); // 2021-12-01T00:00:00Z
        Calendar.Builder builder = new Calendar.Builder();
        // Using reflection to access setInstant method, as it is package-private or protected
        try {
            java.lang.reflect.Method method = Calendar.class.getDeclaredMethod("setInstant", Date.class);
            method.setAccessible(true);
            Calendar resultCal = (Calendar) method.invoke(cal, date);
            if (resultCal != cal) {
                throw new AssertionError("setInstant did not return the same Calendar instance");
            }
            // Verify that the calendar's time is set correctly
            long timeInMillis = cal.getTimeInMillis();
            if (timeInMillis != date.getTime()) {
                throw new AssertionError("Calendar time mismatch: expected " + date.getTime() + " but was " + timeInMillis);
            }
        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Reflection failed to invoke setInstant", e);
        }
    }

    static void testSetInstantWithNull() {
        Calendar cal = Calendar.getInstance();
        try {
            java.lang.reflect.Method method = Calendar.class.getDeclaredMethod("setInstant", Date.class);
            method.setAccessible(true);
            method.invoke(cal, (Object) null);
            throw new AssertionError("Expected NullPointerException when passing null to setInstant");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("Expected NullPointerException, but got: " + e.getCause());
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to invoke setInstant", e);
        }
    }
}
