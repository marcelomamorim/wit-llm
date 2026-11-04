/*
 * @test
 * @summary Verify TimeZoneNamesBundle.handleGetObject inserts the zone ID, passes through non-array values, returns null for missing keys and throws NullPointerException for null keys
 * @modules java.base/sun.util.resources
 * @run main TimeZoneNamesBundleWitupTest
 */

public class TimeZoneNamesBundleWitupTest {

    static class TestBundle extends sun.util.resources.TimeZoneNamesBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"Zone/A", new String[] {"Std", "S", "Dst", "D", "Gen", "G"}},
                {"plain", "value"}
            };
        }
    }

    public static void main(String[] args) {
        TestBundle bundle = new TestBundle();

        Object v = bundle.handleGetObject("Zone/A");
        if (!(v instanceof String[])) {
            throw new RuntimeException("Expected String[] for Zone/A");
        }
        String[] arr = (String[]) v;
        if (arr.length != 7) {
            throw new RuntimeException("Expected length 7 but was " + arr.length);
        }
        if (!"Zone/A".equals(arr[0])) {
            throw new RuntimeException("Zone ID not inserted at index 0: " + arr[0]);
        }
        if (!"Std".equals(arr[1]) || !"G".equals(arr[6])) {
            throw new RuntimeException("Contents not copied correctly");
        }

        Object plain = bundle.handleGetObject("plain");
        if (!"value".equals(plain)) {
            throw new RuntimeException("Non-array value should be returned unchanged");
        }

        if (bundle.handleGetObject("missing") != null) {
            throw new RuntimeException("Missing key should return null");
        }

        try {
            bundle.handleGetObject(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
