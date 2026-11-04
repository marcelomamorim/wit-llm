/*
 * @test
 * @summary Checks public behavior of DateFormatProviderImpl.getDateInstance(int, Locale)
 * @modules java.base/sun.util.locale.provider
 * @run main DateFormatProviderImplWitupTest
 */

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.DateFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class DateFormatProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> langtags = new HashSet<>();
        langtags.add("en-US");
        DateFormatProviderImpl provider =
                new DateFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        int[] styles = {DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL};
        Date date = new Date(0L);

        for (int style : styles) {
            DateFormat df = provider.getDateInstance(style, Locale.US);
            if (df == null) {
                throw new RuntimeException("getDateInstance returned null for style " + style);
            }
            String formatted = df.format(date);
            if (formatted == null || formatted.isEmpty()) {
                throw new RuntimeException("Empty formatted output for style " + style);
            }

            DateFormat df2 = provider.getDateInstance(style, Locale.US);
            if (df == df2) {
                throw new RuntimeException("Expected a new DateFormat instance for style " + style);
            }
        }
    }
}
