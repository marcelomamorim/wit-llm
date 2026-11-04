/*
 * @test
 * @summary Witup-derived checks for BuddhistCalendar.getDisplayNames: ERA names, delegation for non-ERA fields, and invalid field rejection
 * @modules java.base/sun.util
 * @run main BuddhistCalendarWitupTest
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import sun.util.BuddhistCalendar;

public class BuddhistCalendarWitupTest {

    public static void main(String[] args) {
        BuddhistCalendar bc = new BuddhistCalendar(Locale.US);

        Map<String, Integer> eras = bc.getDisplayNames(Calendar.ERA, Calendar.ALL_STYLES, Locale.US);
        if (eras == null || eras.isEmpty()) {
            throw new RuntimeException("ERA display names missing");
        }

        GregorianCalendar gc = new GregorianCalendar(Locale.US);
        Map<String, Integer> expected = gc.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
        Map<String, Integer> actual = bc.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
        if (expected == null || !expected.equals(actual)) {
            throw new RuntimeException("Non-ERA display names must delegate to super");
        }

        expectIllegalArgument(bc, -1, Calendar.ALL_STYLES, Locale.US);
        expectIllegalArgument(bc, Calendar.FIELD_COUNT, Calendar.ALL_STYLES, Locale.US);
    }

    private static void expectIllegalArgument(BuddhistCalendar bc, int field, int style, Locale locale) {
        try {
            bc.getDisplayNames(field, style, locale);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for field=" + field);
    }
}
