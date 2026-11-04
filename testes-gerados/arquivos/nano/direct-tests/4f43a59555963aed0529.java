/* @test    @summary Test getDateInstance method of DateFormatProviderImpl for various styles and locales    @run main DateFormatProviderImplWitupTest */

import java.text.DateFormat;
import java.util.Locale;
import sun.util.locale.provider.DateFormatProviderImpl;

public class DateFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDateInstanceWithStyleAndLocale(DateFormat.SHORT, Locale.US);
        testGetDateInstanceWithStyleAndLocale(DateFormat.MEDIUM, Locale.FRANCE);
        testGetDateInstanceWithStyleAndLocale(DateFormat.LONG, Locale.JAPAN);
        testGetDateInstanceWithStyleAndLocale(DateFormat.FULL, new Locale("ar"));
        testGetDateInstanceWithStyleAndLocale(-1, Locale.CANADA);
    }

    private static void testGetDateInstanceWithStyleAndLocale(int style, Locale locale) {
        try {
            DateFormat df = new DateFormatProviderImpl(LocaleProviderAdapter.Type.LAYOUT, null)
                .getDateInstance(style, locale);
            if (df == null) {
                throw new AssertionError("DateFormat is null for style: " + style + " and locale: " + locale);
            }
            String formatted = df.format(java.util.Calendar.getInstance().getTime());
            System.out.println("Style: " + style + ", Locale: " + locale + ", Formatted date: " + formatted);
        } catch (Exception e) {
            throw new AssertionError("Exception for style: " + style + " and locale: " + locale, e);
        }
    }
}
