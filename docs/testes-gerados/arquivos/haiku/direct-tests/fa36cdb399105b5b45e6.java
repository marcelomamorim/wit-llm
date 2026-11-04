/*
 * @test
 * @summary Verify LocaleData bundle lookup through the public API for the JRE adapter
 * @modules java.base/sun.util.resources java.base/sun.util.locale.provider
 * @run main LocaleDataWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.resources.LocaleData;

public class LocaleDataWitupTest {
    public static void main(String[] args) {
        LocaleData data = new LocaleData(LocaleProviderAdapter.Type.JRE);

        ResourceBundle calendar = data.getCalendarData(Locale.ROOT);
        if (calendar == null) {
            throw new RuntimeException("CalendarData bundle is null for Locale.ROOT");
        }

        ResourceBundle calendarAgain = data.getCalendarData(Locale.ROOT);
        if (calendarAgain == null) {
            throw new RuntimeException("Second CalendarData lookup returned null");
        }

        Object currency = data.getCurrencyNames(Locale.ROOT);
        if (currency == null) {
            throw new RuntimeException("CurrencyNames bundle is null for Locale.ROOT");
        }
    }
}
