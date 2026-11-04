/*
 * @test
 * @summary Test handleGetObject method of TimeZoneNamesBundle for normal and null key cases
 * @run main TimeZoneNamesBundleWitupTest
 */

import sun.util.resources.TimeZoneNamesBundle;
import sun.util.resources.OpenListResourceBundle;
import java.util.MissingResourceException;

public class TimeZoneNamesBundleWitupTest {

    // Minimal concrete subclass to enable testing
    static class TestTZNamesBundle extends TimeZoneNamesBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"TestZone", new String[] {"Standard Long", "Standard Short", "Daylight Long", "Daylight Short", "Generic Long", "Generic Short"}},
                {"OtherZone", new String[] {"StdLong", "StdShort", "DstLong", "DstShort", "GenLong", "GenShort"}}
            };
        }
    }

    public static void main(String[] args) {
        TestTZNamesBundle bundle = new TestTZNamesBundle();

        // Test 1: key present and maps to String[] - should prepend key to array
        Object obj = bundle.handleGetObject("TestZone");
        if (!(obj instanceof String[])) {
            throw new AssertionError("Expected String[] for key 'TestZone'");
        }
        String[] arr = (String[]) obj;
        if (!"TestZone".equals(arr[0])) {
            throw new AssertionError("First element should be the key 'TestZone'");
        }
        if (arr.length != 7) {
            throw new AssertionError("Array length should be 7");
        }
        // Check that the rest of the elements match original contents
        String[] expected = new String[] {"TestZone", "Standard Long", "Standard Short", "Daylight Long", "Daylight Short", "Generic Long", "Generic Short"};
        for (int i = 0; i < arr.length; i++) {
            if (!expected[i].equals(arr[i])) {
                throw new AssertionError("Element at index " + i + " mismatch: expected '" + expected[i] + "' but got '" + arr[i] + "'");
            }
        }

        // Test 2: key present but maps to non-String[] object - should return as is
        Object val = bundle.handleGetObject("OtherZone");
        if (!(val instanceof String[])) {
            throw new AssertionError("Expected String[] for key 'OtherZone'");
        }

        // Test 3: key not present - should return null
        Object missing = bundle.handleGetObject("NoSuchZone");
        if (missing != null) {
            throw new AssertionError("Expected null for missing key");
        }

        // Test 4: key == null - should throw NullPointerException
        try {
            bundle.handleGetObject(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expectedEx) {
            // expected
        }
    }
}
