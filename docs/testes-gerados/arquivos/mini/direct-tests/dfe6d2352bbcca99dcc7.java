/*
 * @test
 * @summary Test sun.util.calendar.JulianCalendar.Date.setEra(Era) method
 * @run main JulianCalendarWitupTest
 */

import sun.util.calendar.JulianCalendar;
import sun.util.calendar.Era;

public class JulianCalendarWitupTest {
    public static void main(String[] args) {
        JulianCalendar.Date date = new JulianCalendar.Date();
        Era[] eras = getErasFromJulianCalendar();

        // Test NullPointerException when era is null
        try {
            date.setEra(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }

        // Test IllegalArgumentException when era is not one of the two known eras
        Era fakeEra = new Era("FakeEra", "FE", 0L, true);
        try {
            date.setEra(fakeEra);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test setting known eras works without exception
        for (int i = 0; i < eras.length; i++) {
            JulianCalendar.Date d = new JulianCalendar.Date();
            d.setEra(eras[i]);
            if (d.getEra() != eras[i]) {
                throw new RuntimeException("Era not set correctly for era index " + i);
            }
        }
    }

    private static Era[] getErasFromJulianCalendar() {
        // Access eras array via reflection since it is private static final
        try {
            java.lang.reflect.Field erasField = JulianCalendar.class.getDeclaredField("eras");
            erasField.setAccessible(true);
            return (Era[]) erasField.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access eras field", e);
        }
    }
}
