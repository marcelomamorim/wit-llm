/*
 * @test
 * @summary Verifies that getGenericDisplayName returns null for an unknown time zone ID
 * @modules java.base/sun.util.locale.provider
 * @run main TimeZoneNameProviderImplWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.TimeZoneNameProviderImpl;

public class TimeZoneNameProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> langtags = new HashSet<>();
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl((LocaleProviderAdapter.Type) null, langtags) {
        };

        String unknownId = "Invalid/Zone_WITUP_NotATimeZone";

        String shortName = provider.getGenericDisplayName(unknownId, java.util.TimeZone.SHORT, Locale.US);
        if (shortName != null) {
            throw new RuntimeException("Expected null for unknown ID (SHORT), got: " + shortName);
        }

        String longName = provider.getGenericDisplayName(unknownId, java.util.TimeZone.LONG, Locale.US);
        if (longName != null) {
            throw new RuntimeException("Expected null for unknown ID (LONG), got: " + longName);
        }
    }
}
