/*
 * @test
 * @summary Verifies BuddhistCalendar.getDisplayNames delegates non-ERA fields to GregorianCalendar and returns Buddhist era names for ERA
 * @modules java.base/sun.util
 * @run main BuddhistCalendarWitupTest
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import sun.util.BuddhistCalendar;

public class BuddhistCalendarWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        BuddhistCalendar bc = new BuddhistCalendar(Locale.US);
        GregorianCalendar gc = new GregorianCalendar(Locale.US);

        Map<String, Integer> bMonths =
            bc.getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.US);
        Map<String, Integer> gMonths =
            gc.getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.US);
        check(bMonths != null, "MONTH names must not be null");
        check(bMonths.equals(gMonths),
              "non-ERA field must delegate to GregorianCalendar");

        Map<String, Integer> bEra =
            bc.getDisplayNames(Calendar.ERA, Calendar.LONG, Locale.US);
        check(bEra != null, "ERA names must not be null");
        check(!bEra.isEmpty(), "ERA names must not be empty");
        check(bEra.containsValue(1), "ERA names must contain Buddhist era value 1");

        Map<String, Integer> viaCalendar =
            ((Calendar) bc).getDisplayNames(Calendar.ERA, Calendar.LONG, Locale.US);
        check(bEra.equals(viaCalendar),
              "Calendar-typed call must dispatch to the BuddhistCalendar override");
    }
}
