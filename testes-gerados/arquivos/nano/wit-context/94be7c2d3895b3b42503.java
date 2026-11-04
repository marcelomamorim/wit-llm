/*
 * @test
 * @summary Tests for DayOfWeek.getDisplayName method, covering null inputs.
 * @run main DayOfWeekWitupTest
 */

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekWitupTest {

    public static void main(String[] args) {
        testGetDisplayName_NullDayOfWeek();
        testGetDisplayName_NullStyle();
    }

    private static void testGetDisplayName_NullDayOfWeek() {
        try {
            DayOfWeek day = null;
            // Expect NullPointerException due to null DayOfWeek
            String result = day.getDisplayName(TextStyle.FULL, Locale.US);
            System.out.println("FAIL: Expected NullPointerException for null DayOfWeek, got result: " + result);
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null DayOfWeek");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null DayOfWeek: " + e);
        }
    }

    private static void testGetDisplayName_NullStyle() {
        try {
            DayOfWeek day = DayOfWeek.MONDAY;
            // Expect NullPointerException due to null TextStyle
            String result = day.getDisplayName(null, Locale.US);
            System.out.println("FAIL: Expected NullPointerException for null TextStyle, got result: " + result);
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null TextStyle");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null TextStyle: " + e);
        }
    }
}
