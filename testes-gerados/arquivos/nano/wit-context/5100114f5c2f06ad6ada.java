/*
 * @test
 * @summary Test for SecurityManager.checkPackageDefinition handling null input
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckPackageDefinitionNull();
    }

    public static void testCheckPackageDefinitionNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageDefinition(null);
            // Expecting NullPointerException due to Objects.requireNonNull
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes if NullPointerException is caught
            System.out.println("NullPointerException caught as expected.");
        } catch (SecurityException e) {
            System.err.println("Unexpected SecurityException: " + e);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
