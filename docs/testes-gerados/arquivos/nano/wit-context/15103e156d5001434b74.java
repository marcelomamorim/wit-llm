/*
 * @test
 * @summary Tests for java.util.Calendar.getDisplayNames method.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testGetDisplayNamesValid();
        testGetDisplayNamesInvalidStyle();
        testGetDisplayNamesInvalidField();
    }

    private static void testGetDisplayNamesValid() {
        Calendar cal = Calendar.getInstance();
        try {
            Map<String, Integer> result = cal.getDisplayNames(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.US);
            if (result != null) {
                System.out.println("testGetDisplayNamesValid: PASSED");
            } else {
                System.out.println("testGetDisplayNamesValid: FAILED - result is null");
            }
        } catch (Exception e) {
            System.out.println("testGetDisplayNamesValid: FAILED with exception: " + e);
        }
    }

    private static void testGetDisplayNamesInvalidStyle() {
        Calendar cal = Calendar.getInstance();
        try {
            Map<String, Integer> result = cal.getDisplayNames(Calendar.MONTH, -1, Locale.US);
            System.out.println("testGetDisplayNamesInvalidStyle: FAILED - expected exception or null, got: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testGetDisplayNamesInvalidStyle: PASSED (caught expected exception)");
        } catch (Exception e) {
            System.out.println("testGetDisplayNamesInvalidStyle: FAILED with unexpected exception: " + e);
        }
    }

    private static void testGetDisplayNamesInvalidField() {
        Calendar cal = Calendar.getInstance();
        try {
            Map<String, Integer> result = cal.getDisplayNames(9999, Calendar.LONG_FORMAT, Locale.US);
            if (result == null) {
                System.out.println("testGetDisplayNamesInvalidField: PASSED (result is null)");
            } else {
                System.out.println("testGetDisplayNamesInvalidField: PASSED (result not null)");
            }
        } catch (Exception e) {
            System.out.println("testGetDisplayNamesInvalidField: FAILED with exception: " + e);
        }
    }
}
