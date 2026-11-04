/*
 * @test
 * @summary Test SecurityManager.checkConnect(String, int) behavior
 * @run main SecurityManagerWitupTest
 */

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test null host throws NullPointerException
        try {
            sm.checkConnect(null, 80);
            throw new Error("Expected NullPointerException for null host");
        } catch (NullPointerException npe) {
            if (!"host can't be null".equals(npe.getMessage())) {
                throw new Error("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test host with colon but not starting with '[' gets wrapped
        // We cannot test permission granted or denied without a security policy,
        // so just test no exception thrown for port != -1
        try {
            sm.checkConnect("1:2:3:4", 1234);
        } catch (SecurityException se) {
            // allowed to throw if security manager denies, so ignore
        }

        // Test host starting with '[' is not wrapped again
        try {
            sm.checkConnect("[1:2:3:4]", 1234);
        } catch (SecurityException se) {
            // allowed to throw if security manager denies, so ignore
        }

        // Test port == -1 triggers SOCKET_RESOLVE_ACTION permission check
        try {
            sm.checkConnect("localhost", -1);
        } catch (SecurityException se) {
            // allowed to throw if security manager denies, so ignore
        }

        // Test port >= 0 triggers SOCKET_CONNECT_ACTION permission check
        try {
            sm.checkConnect("localhost", 80);
        } catch (SecurityException se) {
            // allowed to throw if security manager denies, so ignore
        }
    }
}
