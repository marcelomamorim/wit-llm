/*
 * @test
 * @summary Test getDisplayName method of DayOfWeek for various styles and locales
 * @run main DayOfWeekWitupTest
 */

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekWitupTest {

    public static void main(String[] args) {
        testGetDisplayName(DayOfWeek.MONDAY, TextStyle.FULL, Locale.ENGLISH, "Monday");
        testGetDisplayName(DayOfWeek.TUESDAY, TextStyle.SHORT, Locale.ENGLISH, "Tue");
        testGetDisplayName(DayOfWeek.WEDNESDAY, TextStyle.NARROW, Locale.ENGLISH, "W");
        testGetDisplayName(DayOfWeek.THURSDAY, TextStyle.FULL, Locale.FRENCH, "jeudi");
        testGetDisplayName(DayOfWeek.FRIDAY, TextStyle.SHORT, Locale.FRENCH, "jeu.");
        testGetDisplayName(DayOfWeek.SATURDAY, TextStyle.NARROW, Locale.FRENCH, "s");
        testGetDisplayName(DayOfWeek.SUNDAY, TextStyle.FULL, Locale.GERMAN, "Sonntag");
        testGetDisplayName(DayOfWeek.MONDAY, TextStyle.SHORT, Locale.GERMAN, "Mo");
        testGetDisplayName(DayOfWeek.TUESDAY, TextStyle.NARROW, Locale.GERMAN, "D");
    }

    private static void testGetDisplayName(DayOfWeek day, TextStyle style, Locale locale, String expected) {
        String result = day.getDisplayName(style, locale);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "', but got: '" + result + "' for " + day + " with style " + style + " and locale " + locale);
        }
    }
}
