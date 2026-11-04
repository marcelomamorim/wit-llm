/*
 * @test
 * @summary Test for SecurityManager.checkExec(String) method.
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.File;
import java.security.Permission;
import java.security.SecurityPermission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckExecWithNull();
        testCheckExecWithAbsolutePath();
        testCheckExecWithRelativePath();
    }

    static void testCheckExecWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkExec(null);
            // Expecting NullPointerException
            System.err.println("Expected NullPointerException for null cmd");
        } catch (NullPointerException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    static void testCheckExecWithAbsolutePath() {
        SecurityManager sm = new SecurityManager();
        String absPath = "/bin/ls";
        try {
            sm.checkExec(absPath);
            // No exception expected if permission granted
        } catch (SecurityException e) {
            // Permission denied, acceptable for test
        } catch (NullPointerException e) {
            System.err.println("Unexpected NullPointerException for absolute path");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    static void testCheckExecWithRelativePath() {
        SecurityManager sm = new SecurityManager();
        String relPath = "myScript.sh";
        try {
            sm.checkExec(relPath);
            // No exception expected if permission granted
        } catch (SecurityException e) {
            // Permission denied, acceptable for test
        } catch (NullPointerException e) {
            System.err.println("Unexpected NullPointerException for relative path");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
