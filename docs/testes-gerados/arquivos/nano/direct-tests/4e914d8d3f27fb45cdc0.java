/* @test    @summary Test getGenericDisplayName method of TimeZoneNameProviderImpl    @run main TimeZoneNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.TimeZoneNameProviderImpl;

public class TimeZoneNameProviderImplWitupTest {
    public static void main(String[] args) {
        testGetGenericDisplayName_LongStyle();
        testGetGenericDisplayName_ShortStyle();
        testGetGenericDisplayName_NullNames();
    }

    private static void testGetGenericDisplayName_LongStyle() {
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(null, null);
        String id = "GMT";
        int style = java.util.TimeZone.LONG;
        Locale locale = Locale.US;
        String result = provider.getGenericDisplayName(id, style, locale);
        if (result == null) {
            throw new AssertionError("Expected non-null result for GMT with LONG style");
        }
        System.out.println("testGetGenericDisplayName_LongStyle passed: " + result);
    }

    private static void testGetGenericDisplayName_ShortStyle() {
        TimeZoneNameProviderImpl provider = new TimeZoneNameProviderImpl(null, null);
        String id = "GMT";
        int style = java.util.TimeZone.SHORT;
        Locale locale = Locale.US;
        String result = provider.getGenericDisplayName(id, style, locale);
        if (result == null) {
            throw new AssertionError("Expected non-null result for GMT with SHORT style");
        }
        System.out.println("testGetGenericDisplayName_ShortStyle passed: " + result);
    }

    private static void testGetGenericDisplayName_NullNames() {
        class DummyProvider extends TimeZoneNameProviderImpl {
            DummyProvider() { super(null, null); }
            @Override
            public String[] getDisplayNameArray(String id, Locale locale) {
                return null;
            }
        }
        TimeZoneNameProviderImpl provider = new DummyProvider();
        String id = "InvalidID";
        int style = java.util.TimeZone.LONG;
        Locale locale = Locale.US;
        String result = provider.getGenericDisplayName(id, style, locale);
        if (result != null) {
            throw new AssertionError("Expected null result for invalid ID");
        }
        System.out.println("testGetGenericDisplayName_NullNames passed: null as expected");
    }
}
