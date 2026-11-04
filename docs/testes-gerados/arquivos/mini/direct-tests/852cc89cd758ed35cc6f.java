/*
 * @test
 * @summary Test handleGetObject method of TimeZoneNamesBundle
 * @run main TimeZoneNamesBundleWitupTest
 */

import sun.util.resources.TimeZoneNamesBundle;
import java.util.ListResourceBundle;

public class TimeZoneNamesBundleWitupTest extends TimeZoneNamesBundle {

    // Provide minimal getContents implementation for testing
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
            {"TestZone", new String[] {"Standard Long", "Standard Short", "Daylight Long", "Daylight Short", "Generic Long", "Generic Short"}},
            {"NonArray", "Just a String"}
        };
    }

    public static void main(String[] args) {
        TimeZoneNamesBundleWitupTest bundle = new TimeZoneNamesBundleWitupTest();

        // Test 1: key maps to String[] value
        Object result1 = bundle.handleGetObject("TestZone");
        if (!(result1 instanceof String[])) {
            throw new RuntimeException("Expected String[] for key 'TestZone'");
        }
        String[] arr1 = (String[]) result1;
        if (arr1.length != 7) {
            throw new RuntimeException("Expected array length 7, got " + arr1.length);
        }
        if (!"TestZone".equals(arr1[0])) {
            throw new RuntimeException("Expected first element to be key 'TestZone'");
        }
        // Check that subsequent elements match original contents
        String[] expected = new String[] {"Standard Long", "Standard Short", "Daylight Long", "Daylight Short", "Generic Long", "Generic Short"};
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(arr1[i + 1])) {
                throw new RuntimeException("Mismatch at index " + (i + 1) + ": expected '" + expected[i] + "' but got '" + arr1[i + 1] + "'");
            }
        }

        // Test 2: key maps to non-String[] value, should return value as is
        Object result2 = bundle.handleGetObject("NonArray");
        if (!(result2 instanceof String)) {
            throw new RuntimeException("Expected String for key 'NonArray'");
        }
        if (!"Just a String".equals(result2)) {
            throw new RuntimeException("Expected 'Just a String' for key 'NonArray'");
        }

        // Test 3: key not found, should return null
        Object result3 = bundle.handleGetObject("UnknownKey");
        if (result3 != null) {
            throw new RuntimeException("Expected null for unknown key");
        }

        System.out.println("All tests passed.");
    }
}
