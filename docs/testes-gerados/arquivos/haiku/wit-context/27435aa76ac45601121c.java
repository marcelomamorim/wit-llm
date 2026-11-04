/*
 * @test
 * @summary Verifies that TimeZoneNameUtility.retrieveDisplayNames rejects null id or null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main TimeZoneNameUtilityWitupTest
 */

import java.util.Locale;
import sun.util.locale.provider.TimeZoneNameUtility;

public class TimeZoneNameUtilityWitupTest {

    public static void main(String[] args) {
        expectNpe(null, Locale.US);
        expectNpe("America/New_York", null);
        expectNpe(null, null);
    }

    private static void expectNpe(String id, Locale locale) {
        try {
            TimeZoneNameUtility.retrieveDisplayNames(id, locale);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for id=" + id + ", locale=" + locale);
    }
}
