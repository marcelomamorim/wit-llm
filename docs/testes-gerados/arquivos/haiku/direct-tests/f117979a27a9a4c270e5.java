/*
 * @test
 * @summary Verify getTimeInstance returns non-null time formatters for standard styles
 * @modules java.base/sun.util.locale.provider
 * @run main DateFormatProviderImplWitupTest
 */

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import sun.util.locale.provider.DateFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) {
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.values()[0];
        DateFormatProviderImpl provider =
                new DateFormatProviderImpl(type, Collections.singleton("en-US"));

        int[] styles = {DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL};
        for (int style : styles) {
            DateFormat df = provider.getTimeInstance(style, Locale.US);
            if (df == null) {
                throw new RuntimeException("getTimeInstance returned null for style " + style);
            }
            String formatted = df.format(new Date(0L));
            if (formatted == null || formatted.isEmpty()) {
                throw new RuntimeException("empty formatted output for style " + style);
            }
        }
    }
}
