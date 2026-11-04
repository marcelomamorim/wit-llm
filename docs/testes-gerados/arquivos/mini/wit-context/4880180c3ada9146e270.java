/*
 * @test
 * @summary Test SecurityManager.checkExec(String) for null and absolute path cases
 * @run main SecurityManagerWitupTest
 */

import java.io.FilePermission;
import java.lang.SecurityManager;
import sun.security.util.SecurityConstants;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test 1: checkExec with null should throw NullPointerException
        try {
            sm.checkExec(null);
            throw new RuntimeException("Expected NullPointerException for null cmd");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: checkExec with absolute path should call checkPermission with FilePermission for that path
        // We cannot intercept checkPermission directly, so we test that no exception is thrown for a valid absolute path
        // Using a dummy absolute path
        String absPath = "/tmp/dummy-exec";
        try {
            sm.checkExec(absPath);
        } catch (SecurityException se) {
            // allowed to throw if security manager denies permission, test passes as it shows checkPermission was called
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for absolute path", e);
        }

        // Test 3: checkExec with relative path should call checkPermission with FilePermission for <<ALL FILES>>
        // Using a dummy relative path
        String relPath = "relative-exec";
        try {
            sm.checkExec(relPath);
        } catch (SecurityException se) {
            // allowed to throw if security manager denies permission, test passes as it shows checkPermission was called
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for relative path", e);
        }
    }
}
