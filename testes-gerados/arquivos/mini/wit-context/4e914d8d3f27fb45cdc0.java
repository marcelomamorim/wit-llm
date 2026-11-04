/*
 * @test
 * @summary Test getGenericDisplayName method of TimeZoneNameProviderImpl
 * @run main TimeZoneNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import sun.util.locale.provider.TimeZoneNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class TimeZoneNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetGenericDisplayNameReturnsExpectedName();
        testGetGenericDisplayNameReturnsNullForUnknownId();
        testGetGenericDisplayNameReturnsShortNameWhenStyleIsShort();
    }

    private static void testGetGenericDisplayNameReturnsExpectedName() {
        // Setup: create a TimeZoneNameProviderImpl with a dummy langtags set
        Set<String> langtags = Set.of("en-US");
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Use a known time zone ID and locale
        String id = "GMT";
        Locale locale = Locale.US;

        // The method getDisplayNameArray is package-private or private, so we test via getGenericDisplayName
        // We expect a non-null result for LONG style
        String name = provider.getGenericDisplayName(id, TimeZone.LONG, locale);

        if (name == null) {
            throw new AssertionError("Expected non-null generic display name for LONG style");
        }
    }

    private static void testGetGenericDisplayNameReturnsNullForUnknownId() {
        Set<String> langtags = Set.of("en-US");
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        String unknownId = "Unknown/TimeZone";
        Locale locale = Locale.US;

        String name = provider.getGenericDisplayName(unknownId, TimeZone.LONG, locale);

        if (name != null) {
            throw new AssertionError("Expected null generic display name for unknown time zone ID");
        }
    }

    private static void testGetGenericDisplayNameReturnsShortNameWhenStyleIsShort() {
        Set<String> langtags = Set.of("en-US");
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        String id = "GMT";
        Locale locale = Locale.US;

        String longName = provider.getGenericDisplayName(id, TimeZone.LONG, locale);
        String shortName = provider.getGenericDisplayName(id, TimeZone.SHORT, locale);

        if (longName == null || shortName == null) {
            throw new AssertionError("Expected non-null names for both LONG and SHORT styles");
        }

        if (longName.equals(shortName)) {
            throw new AssertionError("Expected different names for LONG and SHORT styles");
        }
    }
}
