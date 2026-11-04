/*
 * @test
 * @summary Test for getVersions method in ZoneRulesProvider, focusing on null input handling.
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;
import java.util.NavigableMap;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        testGetVersionsNullZoneId();
        testGetVersionsValidZoneId();
    }

    public static void testGetVersionsNullZoneId() {
        try {
            ZoneRulesProvider.getVersions(null);
            System.out.println("testGetVersionsNullZoneId: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testGetVersionsNullZoneId: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetVersionsNullZoneId: FAILED (unexpected exception: " + t + ")");
        }
    }

    public static void testGetVersionsValidZoneId() {
        try {
            String zoneId = "America/New_York";
            NavigableMap<String, ?> versions = ZoneRulesProvider.getVersions(zoneId);
            if (versions != null) {
                System.out.println("testGetVersionsValidZoneId: PASSED");
            } else {
                System.out.println("testGetVersionsValidZoneId: FAILED (null result)");
            }
        } catch (Throwable t) {
            System.out.println("testGetVersionsValidZoneId: FAILED (exception: " + t + ")");
        }
    }
}
