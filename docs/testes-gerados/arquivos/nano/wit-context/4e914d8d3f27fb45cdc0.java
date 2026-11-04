/* @test    @summary Test getGenericDisplayName method for TimeZoneNameProviderImpl    @run main TimeZoneNameProviderImplWitupTest */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.TimeZoneNameProviderImpl;

public class TimeZoneNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetGenericDisplayName_NullNames();
        testGetGenericDisplayName_ValidNames();
    }

    private static void testGetGenericDisplayName_NullNames() {
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(
            null, null);
        String result = provider.getGenericDisplayName("", 0, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null, but got: " + result);
        }
    }

    private static void testGetGenericDisplayName_ValidNames() {
        // Mock getDisplayNameArray to return a fixed array for testing
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(
            null, null) {
            @Override
            protected String[] getDisplayNameArray(String id, Locale locale) {
                return new String[] {"", "", "", "", "", "LongName", "ShortName"};
            }
        };
        String longName = provider.getGenericDisplayName("tzid", 0, Locale.US);
        String shortName = provider.getGenericDisplayName("tzid", 1, Locale.US);
        if (!"LongName".equals(longName)) {
            throw new AssertionError("Expected LongName, but got: " + longName);
        }
        if (!"ShortName".equals(shortName)) {
            throw new AssertionError("Expected ShortName, but got: " + shortName);
        }
    }
}
