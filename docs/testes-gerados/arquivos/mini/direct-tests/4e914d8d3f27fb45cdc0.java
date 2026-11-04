/*
 * @test
 * @summary Test getGenericDisplayName method of TimeZoneNameProviderImpl
 * @run main TimeZoneNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.TimeZone;
import sun.util.locale.provider.TimeZoneNameProviderImpl;
import java.util.HashSet;
import java.util.Set;

public class TimeZoneNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetGenericDisplayNameReturnsCorrectName();
        testGetGenericDisplayNameReturnsNullForNullNames();
    }

    private static void testGetGenericDisplayNameReturnsCorrectName() {
        // Setup a dummy TimeZoneNameProviderImpl with minimal required data
        Set<String> langtags = new HashSet<>();
        langtags.add("en-US");
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags) {
            @Override
            protected String[] getDisplayNameArray(String id, Locale locale) {
                // Provide a dummy array with length >= 7
                // Index 5 for LONG style, index 6 for SHORT style
                return new String[] {"n0", "n1", "n2", "n3", "n4", "longName", "shortName"};
            }
        };

        String longName = provider.getGenericDisplayName("GMT", TimeZone.LONG, Locale.US);
        if (!"longName".equals(longName)) {
            throw new AssertionError("Expected 'longName' for LONG style but got: " + longName);
        }

        String shortName = provider.getGenericDisplayName("GMT", TimeZone.SHORT, Locale.US);
        if (!"shortName".equals(shortName)) {
            throw new AssertionError("Expected 'shortName' for SHORT style but got: " + shortName);
        }
    }

    private static void testGetGenericDisplayNameReturnsNullForNullNames() {
        Set<String> langtags = new HashSet<>();
        langtags.add("en-US");
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags) {
            @Override
            protected String[] getDisplayNameArray(String id, Locale locale) {
                return null;
            }
        };

        String result = provider.getGenericDisplayName("GMT", TimeZone.LONG, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null when getDisplayNameArray returns null");
        }
    }

    // Minimal stub for LocaleProviderAdapter.Type to allow compilation
    static class LocaleProviderAdapter {
        enum Type { JRE }
    }
}
