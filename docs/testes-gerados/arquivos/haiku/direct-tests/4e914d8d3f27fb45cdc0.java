/*
 * @test
 * @summary Checks observable behavior of TimeZoneNameProviderImpl.getGenericDisplayName
 * @modules java.base/sun.util.locale.provider
 * @run main TimeZoneNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.TimeZoneNameProviderImpl;

public class TimeZoneNameProviderImplWitupTest {

    static class TestProvider extends TimeZoneNameProviderImpl {
        TestProvider() {
            super(LocaleProviderAdapter.Type.JRE, Set.of("en-US"));
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        String longName = p.getGenericDisplayName("America/New_York", TimeZone.LONG, Locale.US);
        check(longName != null && !longName.isEmpty(), "LONG generic name must be non-empty for known zone");

        String shortName = p.getGenericDisplayName("America/New_York", TimeZone.SHORT, Locale.US);
        check(shortName != null && !shortName.isEmpty(), "SHORT generic name must be non-empty for known zone");

        String again = p.getGenericDisplayName("America/New_York", TimeZone.LONG, Locale.US);
        check(longName.equals(again), "Repeated calls must return the same name");

        String unknown = p.getGenericDisplayName("Invalid/Zone_XYZ", TimeZone.LONG, Locale.US);
        check(unknown == null, "Unknown zone ID must yield null");
    }
}
