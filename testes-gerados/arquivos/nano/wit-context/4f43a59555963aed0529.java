/*
 * @test
 * @summary Test for getDateInstance method in DateFormatProviderImpl.
 * @run main DateFormatProviderImplWitupTest
 */

import java.text.DateFormat;
import java.util.Locale;
import sun.util.locale.provider.DateFormatProviderImpl;

public class DateFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDateInstanceWithNullLocale();
        testGetDateInstanceWithValidLocale();
    }

    private static void testGetDateInstanceWithNullLocale() {
        try {
            DateFormatProviderImpl provider = new DateFormatProviderImpl(null, null);
            provider.getDateInstance(java.text.DateFormat.SHORT, null);
            System.out.println("FAIL: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null locale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testGetDateInstanceWithValidLocale() {
        try {
            DateFormatProviderImpl provider = new DateFormatProviderImpl(null, null);
            Locale locale = Locale.US;
            DateFormat df = provider.getDateInstance(java.text.DateFormat.SHORT, locale);
            if (df != null && df.getClass().getName().contains("DateFormat")) {
                System.out.println("PASS: getDateInstance returned a DateFormat for locale US");
            } else {
                System.out.println("FAIL: getDateInstance did not return expected DateFormat for locale US");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
