/*
 * @test
 * @summary Test for BuddhistCalendar.getDisplayNames method, including exception path.
 * @run main BuddhistCalendarWitupTest
 */

import sun.util.BuddhistCalendar;
import java.util.Locale;
import java.util.Map;

public class BuddhistCalendarWitupTest {

    public static void main(String[] args) {
        testGetDisplayNamesValid();
        testGetDisplayNamesInvalid();
    }

    static void testGetDisplayNamesValid() {
        try {
            BuddhistCalendar cal = new BuddhistCalendar();
            Map<String, Integer> result = cal.getDisplayNames(BuddhistCalendar.ERA, Calendar.STYLE_SHORT, Locale.US);
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid inputs");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs: " + e);
        }
    }

    static void testGetDisplayNamesInvalid() {
        try {
            BuddhistCalendar cal = new BuddhistCalendar();
            // Triggering IllegalArgumentException by passing invalid style
            cal.getDisplayNames(BuddhistCalendar.ERA, -1, Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            BuddhistCalendar cal = new BuddhistCalendar();
            // Triggering IllegalArgumentException by passing invalid field
            cal.getDisplayNames(9999, Calendar.STYLE_SHORT, Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for invalid field");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
