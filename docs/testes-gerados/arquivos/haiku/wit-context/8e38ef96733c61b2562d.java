/*
 * @test
 * @summary Verifies that Calendar.getWeeksInWeekYear() throws UnsupportedOperationException in the current checkout
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarWitupTest {

    public static void main(String[] args) {
        checkDefaultInstance();
        checkExplicitZoneAndLocale();
    }

    private static void checkDefaultInstance() {
        Calendar cal = Calendar.getInstance();
        expectUnsupported(cal, "getInstance()");
    }

    private static void checkExplicitZoneAndLocale() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        expectUnsupported(cal, "getInstance(TimeZone, Locale)");
    }

    private static void expectUnsupported(Calendar cal, String origin) {
        try {
            int weeks = cal.getWeeksInWeekYear();
            throw new RuntimeException("Expected UnsupportedOperationException for " + origin
                    + " but got value " + weeks);
        } catch (UnsupportedOperationException expected) {
            // expected behavior of the current checkout
        }
    }
}
