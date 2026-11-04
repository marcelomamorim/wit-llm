/*
 * @test
 * @summary Test for sun.util.calendar.ZoneInfoFile.getAliasMap() method.
 * @run main ZoneInfoFileWitupTest
 */

import java.util.Map;
import java.util.Collections;

public class ZoneInfoFileWitupTest {

    public static void main(String[] args) {
        testGetAliasMapWithNullAliases();
        testGetAliasMapWithValidAliases();
    }

    private static void testGetAliasMapWithNullAliases() {
        try {
            // Simulate null aliases by calling getAliasMap() when aliases is null
            // Since aliases is a static field, we cannot directly set it to null without reflection.
            // Instead, we assume the method is called in a context where aliases is null.
            // For testing, we can invoke getAliasMap() and expect a NullPointerException if aliases is null.
            // But as per current code, if aliases is null, getAliasMap() will throw NullPointerException.
            // We test that behavior.
            Map<String, String> result = sun.util.calendar.ZoneInfoFile.getAliasMap();
            // If no exception, the test fails because we expected NullPointerException
            System.out.println("testGetAliasMapWithNullAliases: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testGetAliasMapWithNullAliases: PASSED");
        } catch (Exception e) {
            System.out.println("testGetAliasMapWithNullAliases: FAILED (unexpected exception: " + e + ")");
        }
    }

    private static void testGetAliasMapWithValidAliases() {
        try {
            // To test with valid aliases, we need to set aliases to a non-null map.
            // Since aliases is a static field, and not accessible directly, we cannot set it here.
            // Therefore, this test assumes that getAliasMap() is called in a context where aliases is initialized.
            // Alternatively, we can invoke getAliasMap() and check that it returns an unmodifiable map.
            Map<String, String> result = sun.util.calendar.ZoneInfoFile.getAliasMap();
            if (result != null && Collections.unmodifiableMap(result) == result) {
                System.out.println("testGetAliasMapWithValidAliases: PASSED");
            } else {
                System.out.println("testGetAliasMapWithValidAliases: FAILED (result is null or not unmodifiable)");
            }
        } catch (Exception e) {
            System.out.println("testGetAliasMapWithValidAliases: FAILED (exception: " + e + ")");
        }
    }
}
