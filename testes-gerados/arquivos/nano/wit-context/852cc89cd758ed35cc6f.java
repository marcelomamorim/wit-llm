/* @test    @summary Test handleGetObject with null key to verify NullPointerException behavior    @run main TimeZoneNamesBundleWitupTest */

import sun.util.resources.TimeZoneNamesBundle;

public class TimeZoneNamesBundleWitupTest {

    public static void main(String[] args) {
        testHandleGetObjectWithNullKey();
    }

    private static void testHandleGetObjectWithNullKey() {
        TimeZoneNamesBundle bundle = new TimeZoneNamesBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[0][0];
            }
        };
        try {
            Object result = bundle.handleGetObject(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null key.");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
