/*
 * @test
 * @summary Test WeekFields.of(Locale) method for various locales.
 * @run main WeekFieldsWitupTest
 */
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekFieldsWitupTest {

    public static void main(String[] args) {
        testDefaultLocale();
        testLocaleUS();
        testLocaleFR();
        testLocaleJP();
        testLocaleCustom();
    }

    static void testDefaultLocale() {
        Locale defaultLocale = Locale.getDefault();
        WeekFields wf = WeekFields.of(defaultLocale);
        System.out.println("Default locale: " + defaultLocale);
        System.out.println("First day of week: " + wf.getFirstDayOfWeek());
        System.out.println("Minimal days in first week: " + wf.getMinimalDaysInFirstWeek());
        assert wf != null : "WeekFields.of(Locale) returned null for default locale";
    }

    static void testLocaleUS() {
        Locale us = Locale.US;
        WeekFields wf = WeekFields.of(us);
        System.out.println("Locale US: " + us);
        System.out.println("First day of week: " + wf.getFirstDayOfWeek());
        System.out.println("Minimal days in first week: " + wf.getMinimalDaysInFirstWeek());
        assert wf.getFirstDayOfWeek() == DayOfWeek.SUNDAY : "Expected Sunday as first day for US";
        assert wf.getMinimalDaysInFirstWeek() >= 1 : "Minimal days should be at least 1";
    }

    static void testLocaleFR() {
        Locale fr = Locale.FRANCE;
        WeekFields wf = WeekFields.of(fr);
        System.out.println("Locale FR: " + fr);
        System.out.println("First day of week: " + wf.getFirstDayOfWeek());
        System.out.println("Minimal days in first week: " + wf.getMinimalDaysInFirstWeek());
        assert wf.getFirstDayOfWeek() != null : "First day of week should not be null";
        assert wf.getMinimalDaysInFirstWeek() >= 1 : "Minimal days should be at least 1";
    }

    static void testLocaleJP() {
        Locale jp = Locale.JAPAN;
        WeekFields wf = WeekFields.of(jp);
        System.out.println("Locale JP: " + jp);
        System.out.println("First day of week: " + wf.getFirstDayOfWeek());
        System.out.println("Minimal days in first week: " + wf.getMinimalDaysInFirstWeek());
        assert wf.getFirstDayOfWeek() != null : "First day of week should not be null";
        assert wf.getMinimalDaysInFirstWeek() >= 1 : "Minimal days should be at least 1";
    }

    static void testLocaleCustom() {
        Locale custom = new Locale("xx", "YY");
        WeekFields wf = WeekFields.of(custom);
        System.out.println("Locale Custom: " + custom);
        System.out.println("First day of week: " + wf.getFirstDayOfWeek());
        System.out.println("Minimal days in first week: " + wf.getMinimalDaysInFirstWeek());
        assert wf.getFirstDayOfWeek() != null : "First day of week should not be null";
        assert wf.getMinimalDaysInFirstWeek() >= 1 : "Minimal days should be at least 1";
    }
}
