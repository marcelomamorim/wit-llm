/*
 * @test
 * @summary Exercises LocaleData bundle loading paths that reach the privileged run() action of getBundle
 * @modules java.base/sun.util.resources java.base/sun.util.locale.provider
 * @run main LocaleDataWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.resources.LocaleData;
import sun.util.resources.OpenListResourceBundle;

public class LocaleDataWitupTest {

    public static void main(String[] args) {
        LocaleData data = new LocaleData(LocaleProviderAdapter.Type.JRE);

        ResourceBundle calendar = data.getCalendarData(Locale.US);
        if (calendar == null) {
            throw new RuntimeException("getCalendarData(Locale.US) returned null");
        }

        OpenListResourceBundle currency = data.getCurrencyNames(Locale.US);
        if (currency == null) {
            throw new RuntimeException("getCurrencyNames(Locale.US) returned null");
        }

        ResourceBundle calendarRoot = data.getCalendarData(Locale.ROOT);
        if (calendarRoot == null) {
            throw new RuntimeException("getCalendarData(Locale.ROOT) returned null");
        }
    }
}
