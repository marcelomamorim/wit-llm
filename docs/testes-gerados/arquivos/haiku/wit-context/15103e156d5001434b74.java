/*
 * @test
 * @summary Checks Calendar.getDisplayNames for a valid MONTH lookup and for a field outside the accepted mask, which must return null.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class CalendarWitupTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance(Locale.US);

        Map<String, Integer> months = cal.getDisplayNames(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.US);
        if (months == null || !months.containsValue(Calendar.JANUARY)) {
            throw new RuntimeException("MONTH short names missing JANUARY: " + months);
        }

        Map<String, Integer> hours = cal.getDisplayNames(Calendar.HOUR_OF_DAY, Calendar.LONG_FORMAT, Locale.US);
        if (hours != null) {
            throw new RuntimeException("Expected null for HOUR_OF_DAY, got: " + hours);
        }
    }
}
