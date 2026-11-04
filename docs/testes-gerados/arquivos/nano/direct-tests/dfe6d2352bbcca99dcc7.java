/* @test    @summary Test setEra method of JulianCalendar for valid and invalid eras    @run main JulianCalendarWitupTest */

import sun.util.calendar.JulianCalendar;
import sun.util.calendar.Era;

public class JulianCalendarWitupTest {

    public static void main(String[] args) {
        testSetEraWithValidEras();
        testSetEraWithNull();
        testSetEraWithInvalidEra();
    }

    private static void testSetEraWithValidEras() {
        JulianCalendar cal = new JulianCalendar();
        Era[] eras = getEras(cal);
        for (Era era : eras) {
            try {
                JulianCalendar.Date date = cal.new Date();
                date.setEra(era);
                assert date.getEra() == era : "Era should be set correctly";
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for valid era: " + e);
            }
        }
    }

    private static void testSetEraWithNull() {
        JulianCalendar cal = new JulianCalendar();
        try {
            cal.new Date().setEra(null);
            throw new AssertionError("NullPointerException expected for null era");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testSetEraWithInvalidEra() {
        JulianCalendar cal = new JulianCalendar();
        Era invalidEra = new Era("Invalid", "INV", Long.MAX_VALUE, false);
        try {
            cal.new Date().setEra(invalidEra);
            throw new AssertionError("IllegalArgumentException expected for invalid era");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static Era[] getEras(JulianCalendar cal) {
        try {
            java.lang.reflect.Field erasField = JulianCalendar.class.getDeclaredField("eras");
            erasField.setAccessible(true);
            return (Era[]) erasField.get(cal);
        } catch (Exception e) {
            throw new AssertionError("Failed to access eras field: " + e);
        }
    }
}
