/*
 * @test
 * @summary Test sun.util.calendar.JulianCalendar.Date.setEra(Era) method
 * @run main JulianCalendarWitupTest
 */

import sun.util.calendar.JulianCalendar;
import sun.util.calendar.Era;

public class JulianCalendarWitupTest {
    public static void main(String[] args) {
        testSetEraNull();
        testSetEraInvalid();
        testSetEraValid();
    }

    private static void testSetEraNull() {
        JulianCalendar.Date date = new JulianCalendar.Date();
        try {
            date.setEra(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSetEraInvalid() {
        JulianCalendar.Date date = new JulianCalendar.Date();
        Era invalidEra = new Era("InvalidEra", "IE", 0L, false);
        try {
            date.setEra(invalidEra);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testSetEraValid() {
        JulianCalendar.Date date = new JulianCalendar.Date();
        Era[] validEras = getJulianCalendarEras();
        for (Era era : validEras) {
            JulianCalendar.Date d = new JulianCalendar.Date();
            d.setEra(era);
            if (d.getEra() != era) {
                throw new RuntimeException("Era not set correctly: " + era);
            }
        }
    }

    private static Era[] getJulianCalendarEras() {
        // Access eras via reflection is not allowed; create JulianCalendar instance and get eras indirectly
        JulianCalendar cal = new JulianCalendar();
        try {
            java.lang.reflect.Field erasField = JulianCalendar.class.getDeclaredField("eras");
            erasField.setAccessible(true);
            return (Era[]) erasField.get(cal);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access eras field", e);
        }
    }
}
