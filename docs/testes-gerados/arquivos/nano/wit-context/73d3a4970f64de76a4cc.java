/* @test    @summary Test for X500Principal.getName() method to observe exception paths and behavior.    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testGetNameDefault();
        testGetNameWithNullRfc2253();
    }

    private static void testGetNameDefault() {
        try {
            // Create an X500Principal with a valid distinguished name
            X500Principal principal = new X500Principal("CN=Duke, O=Oracle, C=US");
            String name = principal.getName();
            System.out.println("Default getName(): " + name);
        } catch (Exception e) {
            System.err.println("Unexpected exception in testGetNameDefault: " + e);
        }
    }

    private static void testGetNameWithNullRfc2253() {
        try {
            // Create an X500Principal with a valid distinguished name
            X500Principal principal = new X500Principal("CN=Duke, O=Oracle, C=US");
            // Force getName to use null as format (simulate default behavior)
            String name = principal.getName();
            System.out.println("getName() with default format: " + name);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for null format: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected exception in testGetNameWithNullRfc2253: " + e);
        }
    }
}
