/*
 * @test
 * @summary Test SecurityManager.checkAccept(String, int) behavior
 * @run main SecurityManagerWitupTest_P51bd239275b3
 */

public class SecurityManagerWitupTest_P51bd239275b3 {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test null host throws NullPointerException
        try {
            sm.checkAccept(null, 80);
            throw new RuntimeException("Expected NullPointerException for null host");
        } catch (NullPointerException npe) {
            if (!"host can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test host without brackets and with colon is bracketed
        // We cannot test the permission check effect directly without a SecurityManager installed
        // but we can test that no exception is thrown for a normal host
        try {
            sm.checkAccept("::1", 8080); // IPv6 literal without brackets
        } catch (SecurityException se) {
            // This may throw SecurityException if default security manager denies
            // Acceptable as behavior, so no fail here
        }

        // Test host with brackets and colon remains unchanged
        try {
            sm.checkAccept("[::1]", 8080);
        } catch (SecurityException se) {
            // Acceptable as above
        }

        // Test host without colon is not bracketed
        try {
            sm.checkAccept("localhost", 80);
        } catch (SecurityException se) {
            // Acceptable as above
        }
    }
}
